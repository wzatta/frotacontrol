package com.cilazatta.frotacontrol.service;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cilazatta.frotacontrol.dto.GeolocalizacaoResponseDto;
import com.cilazatta.frotacontrol.dto.PhotonResponseDto;
import com.cilazatta.frotacontrol.exeptions.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeolocalizacaoService {

    private final RestTemplate restTemplate;

    // Cache simples em memória (produção pode virar Redis depois)
    private final Map<String, GeolocalizacaoResponseDto> cache = new ConcurrentHashMap<>();

    public GeolocalizacaoResponseDto buscarCoordenadas(
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf,
            String cep) {

        String cacheKey = String.join("|",
                safe(logradouro),
                safe(numero),
                safe(bairro),
                safe(cidade),
                safe(uf));

        if (cache.containsKey(cacheKey)) {
            log.info("📦 Cache hit para geolocalização: {}", cacheKey);
            return cache.get(cacheKey);
        }

        log.info("📍 Iniciando geocoding");

        // 1️⃣ tentativa completa (mais precisa)
        GeolocalizacaoResponseDto geo = tryGeocode(
                buildFullAddress(logradouro, numero, bairro, cidade, uf  + " Brasil"));

        // 2️⃣ fallback cidade + estado
        if (geo == null) {
            geo = tryGeocode(logradouro +" "+ cidade + " " + uf + " Brasil");
            log.warn("⚠️ Fallback nível 2 (cidade/estado)");
        }

        // 3️⃣ fallback CEP (último recurso)
        if (geo == null && cep != null) {
            geo = tryGeocode(cep + " Brasil");
            log.warn("⚠️ Fallback nível 3 (CEP)");
        }

        if (geo == null) {
            throw new BusinessException("Não foi possível localizar o endereço");
        }

        cache.put(cacheKey, geo);

        log.info("✅ Geolocalização final: lat={}, lon={}", geo.getLat(), geo.getLon());

        return geo;
    }

    public String buscarEnderecoPorCoordenadas(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            log.info("⚠️ Coordenadas nulas recebidas para busca reversa");
            return "";
        }

        try {
            // Concatenação direta idêntica ao seu método que funciona
            String url = "https://photon.komoot.io/reverse?lon=" + longitude +"&lat="+latitude;

            log.info("🌍 Photon Reverse URL: {}", url);

            ResponseEntity<PhotonResponseDto> response =
                    restTemplate.getForEntity(url, PhotonResponseDto.class);

            log.info("📡 HTTP status Reverse: {}", response.getStatusCode());

            PhotonResponseDto body = response.getBody();

            if (body == null || body.getFeatures() == null || body.getFeatures().isEmpty()) {
                log.warn("⚠️ Sem resultados de endereço para lat={}, lon={}", latitude, longitude);
                return "Endereço não localizado";
            }

            var feature = body.getFeatures().get(0);

            if (feature.getProperties() == null) {
                log.warn("⚠️ Nó 'properties' veio vazio no JSON do Photon");
                return "Endereço sem detalhes textuais";
            }

            var properties = feature.getProperties();

            // Monta o texto de forma segura tratando campos nulos
            String rua = properties.getStreet() != null ? properties.getStreet() : "";
            String numero = properties.getHouseNumber() != null ? ", " + properties.getHouseNumber() : "";
            String local = properties.getLocality() != null ? ", " + properties.getLocality() : "";
            String bairro = properties.getDistrict() != null ? " - " + properties.getDistrict() : "";
            String cidade = properties.getCity() != null ? " - " + properties.getCity() : "";
            String estado = properties.getState() != null ? " / " + properties.getState() : "";

            String enderecoCompleto = (rua + local + numero + bairro + cidade + estado).trim();

            // Limpa caracteres órfãos no início caso a rua não venha preenchida
            if (enderecoCompleto.startsWith(",") || enderecoCompleto.startsWith("-")) {
                enderecoCompleto = enderecoCompleto.substring(1).trim();
            }

            return enderecoCompleto.isEmpty() ? "Endereço sem descrição" : enderecoCompleto;

        } catch (Exception e) {
            log.error("❌ Erro no reverse geocoding Photon: {}", e.getMessage(), e);
            return "Erro ao obter endereço";
        }
    }

    
    private GeolocalizacaoResponseDto tryGeocode(String query) {

        try {
            String url = "https://photon.komoot.io/api/?q="
                    + URLEncoder.encode(query, StandardCharsets.UTF_8);

            log.info("🌍 Photon URL: {}", url);

            ResponseEntity<PhotonResponseDto> response =
                    restTemplate.getForEntity(url, PhotonResponseDto.class);

            log.info("📡 HTTP status: {}", response.getStatusCode());

            PhotonResponseDto body = response.getBody();

            if (body == null || body.getFeatures() == null || body.getFeatures().isEmpty()) {
                log.warn("⚠️ Sem resultados para query: {}", query);
                return null;
            }

            var feature = body.getFeatures().get(0);

            if (feature.getGeometry() == null || feature.getGeometry().getCoordinates() == null) {
                return null;
            }

            GeolocalizacaoResponseDto geo = new GeolocalizacaoResponseDto();

            geo.setLon(new BigDecimal(
                    feature.getGeometry().getCoordinates().get(0).toString()));

            geo.setLat(new BigDecimal(
                    feature.getGeometry().getCoordinates().get(1).toString()));

            return geo;

        } catch (Exception e) {
            log.error("❌ Erro no geocoding Photon: {}", e.getMessage(), e);
            return null;
        }
    }

    private String buildFullAddress(String logradouro, String numero, String bairro, String cidade, String uf) {
        return String.format("%s %s %s %s %s Brasil",
                safe(logradouro),
                safe(numero),
                safe(bairro),
                safe(cidade),
                safe(uf));
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}