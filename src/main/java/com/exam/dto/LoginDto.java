package com.exam.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
	
	@NotBlank(message = "username or email mandatory")
	private String usernameOrEmail;
	
	@NotBlank(message = "password is mandatory")
	private String password;

}
