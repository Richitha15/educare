package com.educare.config;

import com.educare.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private static final String[] PUBLIC_URLS = {
        "/api/auth/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/api-docs/**",
        "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_URLS).permitAll()

                // Admin only
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                .requestMatchers("/api/audit-packages/**").hasAnyRole("ADMIN", "AUDITOR")
                .requestMatchers("/api/audit-logs/**").hasAnyRole("ADMIN", "AUDITOR")
                .requestMatchers("/api/kpis/**").hasAnyRole("ADMIN", "AUDITOR")

                // Admin + Exam Coordinator
                .requestMatchers("/api/exams/**").hasAnyRole("ADMIN", "EXAM_COORDINATOR", "TEACHER")
                .requestMatchers("/api/grades/**").hasAnyRole("ADMIN", "EXAM_COORDINATOR", "TEACHER")
                .requestMatchers("/api/grade-changes/**").hasAnyRole("ADMIN", "EXAM_COORDINATOR")

                // Admin + Teacher
                .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers("/api/classes/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT", "PARENT")
                .requestMatchers("/api/teacher-assignments/**").hasAnyRole("ADMIN")

                // Admin + enrollment
                .requestMatchers("/api/students/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                .requestMatchers("/api/enrollments/**").hasAnyRole("ADMIN", "TEACHER")

                // Reports
                .requestMatchers("/api/reports/**").hasAnyRole("ADMIN", "AUDITOR", "EXAM_COORDINATOR")

                // All authenticated users
                .requestMatchers("/api/notifications/**").authenticated()
                .requestMatchers("/api/messages/**").authenticated()
                .requestMatchers("/api/tasks/**").authenticated()

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
