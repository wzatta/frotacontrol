package com.cilazatta.frotacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutheticateRequestDto {
	
	@NotBlank(message = "O Username é obrigatório.")
	private String login;
	
	 @NotBlank(message = "A senha é obrigatória.")
	private String password;

}
