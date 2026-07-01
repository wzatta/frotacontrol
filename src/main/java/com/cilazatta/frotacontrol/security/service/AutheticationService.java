package com.cilazatta.frotacontrol.security.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.AutheticateRequestDto;
import com.cilazatta.frotacontrol.dto.AutheticationResponseDto;
import com.cilazatta.frotacontrol.dto.UsuarioRequestDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Usuario;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.UsuarioRepository;
import com.cilazatta.frotacontrol.security.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AutheticationService {

	private final AuthenticationManager manager;
	private final UsuarioRepository userRepo;
	private final JwtService jwtService;
	private final PasswordEncoder encoder;
	private final FuncionarioRepository colabRepo;

	public AutheticationService(AuthenticationManager manager, UsuarioRepository userRepo, JwtService jwtService,
			FuncionarioRepository colabRepo, PasswordEncoder encoder) {
		this.manager = manager;
		this.userRepo = userRepo;
		this.jwtService = jwtService;
		this.colabRepo = colabRepo;
		this.encoder = encoder;
	}

	public AutheticationResponseDto register(UsuarioRequestDto requestRegister) {

		Funcionario colab = colabRepo.findById(requestRegister.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"" + "Funcionário não encontrado. ID: " + requestRegister.getFuncionarioId()));

		var usuario = Usuario.builder().name(requestRegister.getName()).login(requestRegister.getLogin())
				.funcionario(colab).ativo(requestRegister.getAtivo())
				.senha(this.encoder.encode(requestRegister.getPassword())).roles(List.of(Role.ROLE_USER)).build();

		this.userRepo.save(usuario);

		var jwtToken = this.jwtService.generateToken(usuario);

		return AutheticationResponseDto.builder().token(jwtToken).build();

	}

	public AutheticationResponseDto authetication(AutheticateRequestDto requestAuth) {

		String login = requestAuth.getLogin();
		log.info("Tentando autenticação para login: {}", login);

		log.info("AuthenticationManager injetado: {}", manager.getClass().getName());

		Usuario user = userRepo.findByLogin(login).orElseThrow(() -> {
			log.warn("Usuário não encontrado no banco: {}", login);
			return new UsernameNotFoundException("Usuário não encontrado");
		});

		log.info("senha: {}", user.getSenha());

		String hashCorreto = encoder.encode(requestAuth.getPassword().trim());

		boolean senhaValida = encoder.matches(requestAuth.getPassword().trim(), user.getSenha().trim());

		log.info("senha enviada: {}", requestAuth.getPassword().trim());
		log.info("senha valida: {}", senhaValida);
		log.info("senha para novo teste  no Banco: {}", hashCorreto);

		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(login, requestAuth.getPassword()));
			log.info("Autenticação bem-sucedida para login: {}", login);
		} catch (Exception e) {
			log.error("Falha na autenticação para login: {}. Erro: {}", login, e.getMessage());
			throw e;
		}

		var usuario = userRepo.findByLogin(requestAuth.getLogin()).orElseThrow();

		var jwtToken = this.jwtService.generateToken(usuario);

		return AutheticationResponseDto.builder().token(jwtToken).build();
	}

//	private String extrairLoginDoEmail(String email) {
//		if (email == null || !email.contains("@")) {
//			throw new IllegalArgumentException("Email inválido para gerar login");
//		}
//		return email.substring(0, email.indexOf("@")).toLowerCase();
//	}

}