package com.vehicle_service_spring_v2.auth.services;

import com.vehicle_service_spring_v2.auth.dto.UserLoginDto;
import com.vehicle_service_spring_v2.auth.dto.UserRegisterDto;
import com.vehicle_service_spring_v2.auth.users.User;
import com.vehicle_service_spring_v2.auth.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User signup(UserRegisterDto input) {
        Optional<User> byEmail = userRepository.findByEmail(input.getEmail());

        if (byEmail.isPresent()) {
            return byEmail.get();
        }

        User user = User.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .userName(input.getUserName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();

        var token = jwtService.generateToken(user);
        user.setToken(token);

        log.info("User is created" + user);
        return userRepository.save(user);
    }

    public User authenticate(UserLoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found by this email"));
    }
}
