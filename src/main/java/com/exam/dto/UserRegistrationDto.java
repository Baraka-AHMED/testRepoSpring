package com.exam.dto;

import com.exam.model.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Lombok génère les getters, setters, toString, equals, hashcode
@Data
public class UserRegistrationDto {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @NotNull(message = "Role is mandatory")
    private UserRole role;

}
