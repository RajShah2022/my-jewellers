package com.my_jewellers.my_jewellers.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Password length should be 8 to 20 characters")
    private String password;
    @NotEmpty(message = "Address is mandatory")
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotEmpty(message = "State Name is mandatory")
    @NotBlank(message = "State Name is mandatory")
    private String statename;


}
