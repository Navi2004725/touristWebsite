package com.example.touristWebsite.controller;

import com.example.touristWebsite.authentication.CustomUserDetails;
import com.example.touristWebsite.dto.AuthRequestDTO;
import com.example.touristWebsite.dto.AuthResponseDTO;
import com.example.touristWebsite.entity.UserEntity;
import com.example.touristWebsite.repo.UserRepo;
import com.example.touristWebsite.service.impl.CustomUserDetailsService;
import com.example.touristWebsite.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final UserRepo userRepo;



    public AuthController(AuthenticationManager authManager,
                          CustomUserDetailsService userDetailsService,
                          JWTUtil jwtUtil, UserRepo userRepo) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        // Authenticate user
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Generate JWT
        String token = jwtUtil.generateToken(userDetails);

        // Extract role from UserEntity
        UserEntity userEntity = ((CustomUserDetails) userDetails).getUser();

        // Return token, email, and role
        AuthResponseDTO response = new AuthResponseDTO(token, userEntity.getEmail(), userEntity.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponseDTO> signUp(@RequestBody Map<String, String> request) {
        try {
            String accessToken = request.get("token");

            // Call Google's UserInfo endpoint
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;

            Map userInfo = restTemplate.getForObject(url, Map.class);

            String email = (String) userInfo.get("email");

            UserEntity user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                user = new UserEntity();
                user.setEmail(email);
                user.setPassword("");
                user.setRole("user");
                userRepo.save(user);
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponseDTO(jwt, email, user.getRole()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }


    }
}

