package com.example.touristWebsite.config;

import com.example.touristWebsite.authentication.JwtAuthenticationFilter;
import com.example.touristWebsite.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",      // Local development (Vite)
                "http://localhost:3000",      // Local development (Create React App)
                "https://touristwebsite-2.onrender.com" // Your Vercel URL
        )); // React dev server
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable()) // (This can disable the protection of domain)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth-> auth.requestMatchers( "/", "/auth/**").permitAll()
                        .requestMatchers("/hotels/").permitAll() // Everyone can view hotels
                        .requestMatchers("/hotels/search/**").permitAll()
                        .requestMatchers("/hotels/**").authenticated() // Other hotel operations need authentication
                        .requestMatchers("/rooms/").permitAll() // Everyone can view rooms
                        .requestMatchers("/rooms/update/**").permitAll()
                        .requestMatchers("/rooms/search/**").permitAll()
                        .requestMatchers("/uploads").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/payment/rooms/create").permitAll()
                        .requestMatchers("/payment/rooms/{bookingId}/{paymentId}").permitAll()
                        .requestMatchers("/rooms/**").authenticated()
                        .requestMatchers("/payment/rooms/**").authenticated()
                        // Booking rules
                        .requestMatchers("/booking/create").permitAll() // anyone can add booking
                        .requestMatchers("/booking/").authenticated()  // only admins can view all
                        .requestMatchers("/booking/**").permitAll()   // anyone can view by ID
                        .anyRequest().authenticated()).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    // 👇 Remove default "ROLE_" prefix
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // no prefix
    }



}
