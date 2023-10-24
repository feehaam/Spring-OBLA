package com.feeham.obla.security;

import com.feeham.obla.utilities.constants.APIConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->{
                    auth
                            .requestMatchers(HttpMethod.POST, APIConstants.SIGN_IN,APIConstants.SIGN_UP, APIConstants.SIGN_UP_ADMIN).permitAll()
                            .requestMatchers(HttpMethod.GET, "/user/all").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.GET, "/users/{userId}").authenticated()
//                            .requestMatchers(HttpMethod.GET, "/users/profile").authenticated()
//                            .requestMatchers(HttpMethod.GET, "/users/{userId}/books").hasAnyRole("ADMIN", "CUSTOMER")
//                            .requestMatchers(HttpMethod.GET, "/users/{userId}/borrowed-books").hasAnyRole("ADMIN", "CUSTOMER")
                            .requestMatchers(HttpMethod.POST, "/books/create").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/books/update/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/books/delete/{id}").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.GET, "/books/getAll").hasAnyRole("ADMIN", "CUSTOMER")
                            .requestMatchers(HttpMethod.POST, "/books/{bookId}/borrow").hasRole("CUSTOMER")
                            .requestMatchers(HttpMethod.DELETE, "/books/{bookId}/return").hasRole("CUSTOMER")
                            .requestMatchers(HttpMethod.GET, "/books/{bookId}/reserve").hasRole("CUSTOMER")
                            .requestMatchers(HttpMethod.GET, "/books/{bookId}/cancel-reservation").hasRole("CUSTOMER")
                            .requestMatchers(HttpMethod.POST, "/books/{bookId}/reviews/create").hasRole("CUSTOMER")
//                            .requestMatchers(HttpMethod.GET, "/books/{bookId}/reviews").hasAnyRole("CUSTOMER", "ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/books/reviews/{reviewId}/update").hasRole("CUSTOMER")
                            .requestMatchers(HttpMethod.DELETE, "/books/reviews/{reviewId}/delete").hasRole("CUSTOMER")
                            .requestMatchers(HttpMethod.DELETE, "/books/{bookId}/reviews/{reviewId}/delete").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/books/{bookId}").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.DELETE, "/users/{userId}/history").hasAnyRole("CUSTOMER", "ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
