package com.vehicle_service_spring_v2.auth.resource;

import com.vehicle_service_spring_v2.auth.dto.UserLoginDto;
import com.vehicle_service_spring_v2.auth.dto.UserRegisterDto;
import com.vehicle_service_spring_v2.auth.services.AuthenticationService;
import com.vehicle_service_spring_v2.auth.services.JwtService;
import com.vehicle_service_spring_v2.auth.users.LoginResponse;
import com.vehicle_service_spring_v2.auth.users.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST API for authentication and authorization",
        description = "Provides resource methods for managing users"
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Create a new user",
            description = "It is used to create new user",
            parameters = {
                    @Parameter(
                            name = "registerUserDto",
                            description = "registerUserDto object",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully created"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(
            summary = "Logging for existed users",
            description = "It is used to login a user",
            parameters = {
                    @Parameter(
                            name = "userLoginDto",
                            description = "userLoginDto object",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User is found"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
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
