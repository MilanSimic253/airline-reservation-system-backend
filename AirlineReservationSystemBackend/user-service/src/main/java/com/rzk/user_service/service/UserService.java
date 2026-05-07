package com.rzk.user_service.service;

import com.rzk.user_service.dto.LoginRequest;
import com.rzk.user_service.dto.RegisterRequest;
import com.rzk.user_service.model.User;
import com.rzk.user_service.repository.UserRepository;
import com.rzk.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Korisnik već postoji!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
        return "Registracija uspešna!";
    }

    public String registerAdmin(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Korisnik već postoji!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ADMIN")
                .build();

        userRepository.save(user);
        return "Registracija uspešna!";
    }


    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Pogrešno korisničko ime!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Pogrešna lozinka!");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
