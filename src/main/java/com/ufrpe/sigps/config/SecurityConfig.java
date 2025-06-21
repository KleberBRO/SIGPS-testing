package com.ufrpe.sigps.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.ufrpe.sigps.model.Role.ADMIN;
import static com.ufrpe.sigps.model.Role.INVENTOR;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // Permite usar @PreAuthorize e @PostAuthorize nos métodos dos controladores
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs RESTful sem sessões
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (autenticação e registro)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Endpoints para Inventores
                        .requestMatchers(GET, "/api/v1/intellectual-properties/**").hasAnyRole(ADMIN.name(), INVENTOR.name())
                        .requestMatchers(POST, "/api/v1/intellectual-properties/**").hasRole(INVENTOR.name())
                        .requestMatchers(PUT, "/api/v1/intellectual-properties/**").hasRole(INVENTOR.name())
                        .requestMatchers(DELETE, "/api/v1/intellectual-properties/**").hasRole(INVENTOR.name())
                        .requestMatchers(GET, "/api/v1/startups/**").hasAnyRole(ADMIN.name(), INVENTOR.name())
                        .requestMatchers(POST, "/api/v1/startups/**").hasRole(INVENTOR.name())
                        .requestMatchers(PUT, "/api/v1/startups/**").hasRole(INVENTOR.name())
                        .requestMatchers(DELETE, "/api/v1/startups/**").hasRole(INVENTOR.name())


                        // Endpoints para Administradores
                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers("/api/v1/users/**").hasRole(ADMIN.name()) // Gerenciamento de usuários
                        // Adicione outras regras de autorização conforme necessário

                        // Qualquer outra requisição requer autenticação
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Não usa sessões HTTP
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT

        return http.build();
    }

    // Configuração de CORS para permitir requisições do frontend React
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173")); // Adicione aqui a URL do seu frontend React
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}