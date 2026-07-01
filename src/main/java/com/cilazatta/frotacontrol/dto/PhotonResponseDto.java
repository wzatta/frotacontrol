package com.cilazatta.frotacontrol.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotonResponseDto {

    private List<Feature> features;
    

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {

        private Geometry geometry;
        private Properties properties;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {

        private List<BigDecimal> coordinates;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {
        private String street;
        
        @JsonProperty("housenumber")
        private String houseNumber;
        private String locality;
        private String district;
        private String city;
        private String state;
    }
    
}