package com.example.homebudget.domain.users;

import java.util.List;


public interface UserRegistrationService {

    List<String> defaultRoles = List.of("USER");

    BudgetAppUser registerNewUser(String userName, String rawPassword, String email, List<String> roles);
}
