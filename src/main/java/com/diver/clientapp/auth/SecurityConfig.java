package com.diver.clientapp.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authHttp) -> authHttp
                        .requestMatchers(HttpMethod.GET, "/authorized").permitAll() // Permitir acceso sin autenticación
                        .requestMatchers(HttpMethod.GET, "/list").hasAnyAuthority("SCOPE_read", "SCOPE_write") // Acceso restringido
                        .requestMatchers(HttpMethod.POST, "/create").hasAuthority("SCOPE_write") // Acceso restringido
                        .anyRequest().authenticated()) // Todo lo demás necesita autenticación
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF para simplificar el desarrollo
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin estado
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app")) // Configurar OAuth2
                .oauth2Client(withDefaults()) // Configurar cliente OAuth2
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults())); // Configurar servidor de recursos

        return http.build();
    }
}
