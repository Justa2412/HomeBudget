package com.example.homebudget.infrastructure;

import com.example.homebudget.domain.users.BudgetAppUser;

import java.util.Optional;

public interface UserRepository {
    Optional<BudgetAppUser>  findByName(String name);
    BudgetAppUser save(BudgetAppUser budgetAppUser);
    boolean existsByEmailOrName(String email, String name);
}
