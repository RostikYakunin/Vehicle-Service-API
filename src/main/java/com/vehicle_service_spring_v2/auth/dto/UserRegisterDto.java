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
public class UserRegisterDto {
    @NotEmpty(message = "Field \"firstname\" can not be empty ")
    @Size(min = 2, max = 100, message = "Your first name must be between 2 to 100 characters")
    private String firstName;

    @NotEmpty(message = "Field \"lastname\" can not be empty ")
    @Size(min = 2, max = 100, message = "Your last name must be between 2 to 100 characters")
    private String lastName;

    @NotEmpty(message = "Field \"username\" can not be empty ")
    @Size(min = 2, max = 100, message = "Your user name must be between 2 to 100 characters")
    private String userName;

    @NotEmpty(message = "Field \"email\" can not be empty ")
    @Email(message = "The email address is invalid.", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Size(min = 8, max = 100)
    @NotEmpty(message = "Field \"password\" can not be empty ")
    private String password;

}
