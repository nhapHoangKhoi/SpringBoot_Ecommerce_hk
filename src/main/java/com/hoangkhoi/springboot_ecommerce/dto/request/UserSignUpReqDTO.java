package com.hoangkhoi.springboot_ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpReqDTO {
    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 5, max = 60)
    private String password;

    @NotBlank(message = "First name cannot be blank!")
    @Size(max = 50, message = "First name must not exceed {max} characters!")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank!")
    @Size(max = 50, message = "Last name must not exceed {max} characters!")
    private String lastName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number!")
    @Size(max = 15, message = "Phone must not exceed {max} characters!")
    private String phone;

    @NotBlank(message = "Address cannot be blank!")
    @Size(max = 255, message = "Address must not exceed {max} characters!")
    private String address;
}
