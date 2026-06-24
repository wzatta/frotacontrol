package com.cilazatta.frotacontrol.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private final JwtAutheticationFilter jwtAuthFilter;

	public SecurityConfiguration(JwtAutheticationFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            // 🔹 liberar Auth e Swagger
	            .requestMatchers(
	                "/api/v1/auth/authenticate",
	                "/v3/api-docs/**",
	                "/v3/api-docs",
	                "/api-docs/**",
	                "/api-docs",
	                "api-docs",
	                "/swagger-ui/**",
	                "/swagger-ui.html",
	                "/swagger-resources/**",
	                "/webjars/**"
	            ).permitAll()
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();

	    // ✅ Domínios permitidos
	    config.setAllowedOrigins(List.of("http://localhost:4200", "https://front-pi-flame.vercel.app"));

	    // ✅ Métodos HTTP permitidos
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

	    // ✅ Cabeçalhos permitidos e expostos
	    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
	    config.setExposedHeaders(List.of("Authorization"));

	    // ✅ Permite envio de cookies/token de autenticação
	    config.setAllowCredentials(true);

	    // ✅ Tempo de cache do preflight
	    config.setMaxAge(3600L);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
	
	
	
	

}