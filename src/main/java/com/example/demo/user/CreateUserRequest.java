package com.example.demo.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String firstname,
        @NotBlank String lastname
) {

}
