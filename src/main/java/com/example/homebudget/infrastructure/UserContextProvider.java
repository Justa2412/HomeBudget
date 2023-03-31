package com.example.homebudget.infrastructure;

import com.example.homebudget.domain.users.BudgetAppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public enum UserContextProvider {
    INSTANCE;

    public static BudgetAppUser getUserContext() {
        return (BudgetAppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
