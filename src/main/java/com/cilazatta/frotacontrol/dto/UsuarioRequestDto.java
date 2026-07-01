package com.cilazatta.frotacontrol.dto;

import java.util.List;

import com.cilazatta.frotacontrol.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDto {
	
	
	@NotBlank(message = "Nome é obrigatório")
    private String name;
	
	 @NotBlank(message = "Login é obrigatório")
	 private String login;
	
	private Long funcionarioId;
	
	private Long empresaId;

	 private Boolean ativo;
	    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter ao menos 6 caracteres")
    private String password;
    
    @NotNull(message = "Pelo menos uma role é obrigatória")
    private List<Role> roles;

}
