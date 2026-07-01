package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.UsuarioNomeResponseDto;
import com.cilazatta.frotacontrol.dto.UsuarioRequestDto;
import com.cilazatta.frotacontrol.dto.UsuarioResponseDto;
import com.cilazatta.frotacontrol.dto.UsuarioUpdateRequestDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Usuario;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.exeptions.AccessDeniedException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.RegisterRequestMapper;
import com.cilazatta.frotacontrol.mapper.UsuarioMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.UsuarioRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	private final RegisterRequestMapper mapper;
	private final UsuarioRepository userRepo;
	private final FuncionarioRepository colabRepo;
	private final EmpresaRepository empresaRepo;
	private final PasswordEncoder passwordEncoder;
	private final UsuarioMapper userMapper;
	private final UsuarioLogadoService userLogado;

	// 🔹 Construtor explícito para injeção das dependências
	public UsuarioService(RegisterRequestMapper mapper, UsuarioLogadoService userLogado, UsuarioMapper userMapper,
			EmpresaRepository empresaRepo, UsuarioRepository userRepo, FuncionarioRepository colabRepo,
			PasswordEncoder passwordEncoder) {
		this.mapper = mapper;
		this.userLogado = userLogado;
		this.empresaRepo = empresaRepo;
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.colabRepo = colabRepo;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Cria um novo usuário com base no DTO recebido.
	 */
	@Transactional
	public UsuarioResponseDto createUser(UsuarioRequestDto dto) {

		List<Role> rolesLogado = userLogado.getRoles(); // agora enum

		Empresa empresa = this.verificarEmpresa(dto);

		Funcionario colaborador = colabRepo.findById(dto.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado."));

		Usuario usuario = mapper.toEntity(dto, colaborador);

		List<Role> usuarioRoles = usuario.getRoles();

		this.validarRolesCriacaoUsuario(rolesLogado, usuarioRoles);

		usuario.setEmpresa(empresa);

		Usuario savedUser = userRepo.save(usuario);
		log.info("Usuário criado com sucesso: {}", savedUser.getLogin());
		return userMapper.toResponse(savedUser);
	}

	
	/**
	 * Atualiza campos não sensíveis de um usuário existente (exceto senha).
	 */
	@Transactional
	public UsuarioResponseDto updateUser(Long id, UsuarioUpdateRequestDto dto) {

		if(!userLogado.hasRole(Role.ROLE_ADMIN_EMPRESA)){
			 throw new AccessDeniedException(
				        "Apenas usuários com perfil ROLE_ADMIN_EMPRESA podem realizar esta operação."
				    );
		}
		
		Usuario usuario = userRepo.findByIdAndEmpresaId(id, userLogado.getEmpresaId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

		usuario.setName(dto.getName() != null ? dto.getName() : usuario.getName());
		//usuario.setLogin(dto.getLogin() != null ? dto.getLogin() : usuario.getLogin());
		
		List<Role> rolesLogado = userLogado.getRoles(); // agora enum
		List<Role> novoUsuarioRoles = dto.getRoles();

		this.validarRolesCriacaoUsuario(rolesLogado, novoUsuarioRoles);

		
		usuario.setRoles(dto.getRoles() != null ? dto.getRoles() : usuario.getRoles());
		usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : usuario.getAtivo());

		Usuario updatedUser = userRepo.save(usuario);
		log.info("Usuário atualizado com sucesso: {}", updatedUser.getLogin());
		return userMapper.toResponse(updatedUser);
	}

	/**
	 * Atualiza apenas a senha de um usuário.
	 */
	@Transactional
	public void updatePassword(Long id, String novaSenha) {
		Usuario usuario = userRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

		usuario.setSenha(passwordEncoder.encode(novaSenha));
		userRepo.save(usuario);

		log.info("Senha atualizada com sucesso para o usuário: {}", usuario.getLogin());
	}
	
	public UsuarioNomeResponseDto buscarNomeUsuario() {
		
		Usuario user = userRepo.findByIdAndEmpresaId(userLogado.getUsuario().getId(), userLogado.getEmpresaId())
				.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado."));;
		UsuarioNomeResponseDto nomeUsuario = new UsuarioNomeResponseDto();
		nomeUsuario.setName(user.getFuncionario() != null ? user.getFuncionario().getNome() : user.getName());
		return nomeUsuario;
	}


	private void validarRolesCriacaoUsuario(List<Role> rolesLogado, List<Role> rolesNovoUsuario) {

	    boolean isAdminEmpresa = rolesLogado.contains(Role.ROLE_ADMIN_EMPRESA);

	    if (!isAdminEmpresa) {
	        return; // outros perfis podem ter regra própria
	    }

	    for (Role role : rolesNovoUsuario) {

	        if (role == Role.ROLE_ADMIN) {
	            throw new AccessDeniedException(
	                "ROLE_ADMIN_EMPRESA não pode atribuir ROLE_ADMIN"
	            );
	        }
	    }
	}
	
	private Empresa verificarEmpresa(UsuarioRequestDto dto) {

	    List<Role> roles = userLogado.getRoles();

	    boolean isAdmin = roles.contains(Role.ROLE_ADMIN);
	    boolean isAdminEmpresa = roles.contains(Role.ROLE_ADMIN_EMPRESA);

	    Long empresaId;

	    if (isAdmin) {
	        empresaId = dto.getEmpresaId();

	        if (empresaId == null) {
	            throw new RuntimeException("ADMIN deve informar empresaId");
	        }

	    } else if (isAdminEmpresa) {
	        empresaId = userLogado.getEmpresaId();

	    } else {
	        throw new RuntimeException("Usuário sem permissão para definir empresa");
	    }

	    return empresaRepo.findById(empresaId)
	            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
	}

}
