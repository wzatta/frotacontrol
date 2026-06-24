package com.cilazatta.frotacontrol.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cilazatta.frotacontrol.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationConfig {

    private final UsuarioRepository userRepo;

    public ApplicationConfig(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            log.info("Chamando UserDetailsService para login: {}", username);
            return userRepo.findByLogin(username)
                    .orElseThrow(() -> {
                        log.warn("Usuário não encontrado no banco: {}", username);
                        return new UsernameNotFoundException("Usuário não encontrado");
                    });
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Novo padrão (sem DaoAuthenticationProvider)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
