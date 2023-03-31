package com.example.homebudget.api.users;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


record UserRegistrationRequest(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank

        String password,

        @Email
        String email
) {
}
