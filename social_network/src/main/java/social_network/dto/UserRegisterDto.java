package social_network.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(

        @NotBlank(message = "name not be null and not empty and not blank")
        @Size(max = 50, message = "name cannot be less than 50 characters")
        String name,

        @NotBlank(message = "surname not be null and not empty and not blank")
        @Size(max = 50, message = "surname cannot be less than 50 characters")
        String surname,

        @NotBlank(message = "login not be null and not empty and not blank")
        @Size(max = 254, message = "login cannot be less than 254 characters")
        @Email(message = "incorrect login")
        String login,

        @NotBlank(message = "password not be null and not empty and not blank")
        @Size(min = 8, max = 72, message = "password length cannot be less than 8 or more than 72 characters")
        String password
) {}
