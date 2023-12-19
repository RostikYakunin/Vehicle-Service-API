package com.vehicle_service_spring_v2.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserLoginDto {
    @NotEmpty(message = "Field \"email\" can not be empty ")
    @Email(message = "The email address is invalid.", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Size(min = 8, max = 100)
    @NotEmpty(message = "Field \"password\" can not be empty ")
    private String password;
}
