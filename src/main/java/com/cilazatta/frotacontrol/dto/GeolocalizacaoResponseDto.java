package com.cilazatta.frotacontrol.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class GeolocalizacaoResponseDto {

    private BigDecimal lat;

    private BigDecimal lon;

//    public BigDecimal getLat() { return lat; }
//    public BigDecimal getLon() { return lon; }
}