package com.vehicle_service_spring_v_3.auth.resource;

import com.vehicle_service_spring_v_3.auth.dto.UserLoginDto;
import com.vehicle_service_spring_v_3.auth.dto.UserRegisterDto;
import com.vehicle_service_spring_v_3.auth.services.AuthenticationService;
import com.vehicle_service_spring_v_3.auth.services.JwtService;
import com.vehicle_service_spring_v_3.auth.users.LoginResponse;
import com.vehicle_service_spring_v_3.auth.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserLoginDto userLoginDto) {
        User authenticatedUser = authenticationService.authenticate(userLoginDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
