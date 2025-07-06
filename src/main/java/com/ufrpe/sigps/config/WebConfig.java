// src/main/java/com/ufrpe/sigps/config/WebConfig.java
package com.ufrpe.sigps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a configuração a todos os endpoints sob /api/
                .allowedOrigins("*")   // Para testes, permite qualquer origem. Em produção, mude para o seu domínio (ex: "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite todos os métodos HTTP necessários
                .allowedHeaders("*")   // Permite todos os headers
                .allowCredentials(false); // Mude para true se precisar de cookies/autenticação baseada em sessão
    }
}