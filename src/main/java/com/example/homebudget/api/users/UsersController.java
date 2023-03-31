package com.example.homebudget.api.users;

import com.example.homebudget.domain.users.BudgetAppUser;
import com.example.homebudget.domain.users.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController

@RequestMapping("/users")

class UsersController {

    private final UserRegistrationService registrationService;


    UsersController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping
    ResponseEntity<UserDetailsResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request
    ) {
        BudgetAppUser budgetAppUser = registrationService.registerNewUser(request.name(), request.password(), request.email(), UserRegistrationService.defaultRoles);
        return ResponseEntity.ok(UserDetailsResponse.fromDomain(budgetAppUser));
    }
}
