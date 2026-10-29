package com.sharemeal.dto;

import com.sharemeal.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Java record used as an immutable DTO; validated with Bean Validation annotations. */
public record RegistrationRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Email format is invalid") String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters") String password,
        @Pattern(regexp = "\\d{10}", message = "Phone must be exactly 10 digits") String phone,
        @NotBlank(message = "Address is required") String address,
        Role role) {
}
