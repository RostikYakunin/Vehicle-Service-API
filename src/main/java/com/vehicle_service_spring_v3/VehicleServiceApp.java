package com.vehicle_service_spring_v3;

import com.vehicle_service_spring_v3.auth.dto.UserRegisterDto;
import com.vehicle_service_spring_v3.auth.services.AuthenticationService;
import com.vehicle_service_spring_v3.auth.services.JwtService;
import com.vehicle_service_spring_v3.auth.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class VehicleServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(VehicleServiceApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService authenticationService, JwtService jwtService) {
        return args -> {
            var admin = UserRegisterDto.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .userName("Admin")
                    .email("admin@gmail.com")
                    .password("pass")
                    .build();
            User signup = authenticationService.signup(admin);
            String token = jwtService.generateToken(signup);
            log.info("Admin token: " + token);
        };
    }
}
