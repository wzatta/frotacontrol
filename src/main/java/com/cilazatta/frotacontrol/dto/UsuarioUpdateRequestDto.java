package com.cilazatta.frotacontrol.dto;

import java.util.List;

import com.cilazatta.frotacontrol.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateRequestDto {
	
	
    private String name;
	
	 private Boolean ativo;
	    
   
    private List<Role> roles;

}
