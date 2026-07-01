package com.cilazatta.frotacontrol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.VeiculoAutorizadoDto;
import com.cilazatta.frotacontrol.dto.VeiculoCondutorRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoCondutorResponseDto;
import com.cilazatta.frotacontrol.service.VeiculoCondutorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/veiculos-condutores")
@RequiredArgsConstructor
@Validated
public class VeiculoCondutorController {

	private final VeiculoCondutorService service;

	@PostMapping
	public ResponseEntity<VeiculoCondutorResponseDto> salvar(@Valid @RequestBody VeiculoCondutorRequestDto request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<VeiculoCondutorResponseDto> atualizar(
			@PathVariable @Positive(message = "Id deve ser maior que zero") Long id,

			@Valid @RequestBody VeiculoCondutorRequestDto request) {

		return ResponseEntity.ok(service.atualizar(id, request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<VeiculoCondutorResponseDto> buscarPorId(
			@PathVariable @Positive(message = "Id deve ser maior que zero") Long id) {

		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@GetMapping("/veiculo/{veiculoId}")
	public ResponseEntity<List<VeiculoCondutorResponseDto>> listarPorVeiculo(
			@PathVariable @Positive(message = "Id do veículo deve ser maior que zero") Long veiculoId) {

		return ResponseEntity.ok(service.listarPorVeiculo(veiculoId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable @Positive(message = "Id deve ser maior que zero") Long id) {

		service.excluir(id);

		return ResponseEntity.noContent().build();
	}
	
	 /**
     * Retorna a lista de veículos autorizados para um motorista específico em uma empresa.
     * Exemplo de chamada: GET /api/v1/veiculos-autorizados?funcionarioId=5&empresaId=1
     */
    @GetMapping
    public ResponseEntity<List<VeiculoAutorizadoDto>> listarVeiculosAutorizados() {

        List<VeiculoAutorizadoDto> response = service
                .listarVeiculosAutorizadosParaMotorista();
        return ResponseEntity.ok(response);
    }
	
	

}
