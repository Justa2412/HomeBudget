package com.example.homebudget.domain.budgets;

import com.example.homebudget.domain.budgettypes.BudgetType;
import com.example.homebudget.domain.users.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Optional;


public interface BudgetService {
    Budget registerNewBudget(String name, UserId userId, BigDecimal cap, BudgetType budgetType);

    Optional<Budget> getBudget(BudgetId budgetId, UserId userId);

    Optional<Budget> updateBudget(BudgetId budgetId, String name, UserId userId, BigDecimal cap, BudgetType budgetType);

    void deleteBudget(BudgetId budgetId, UserId userId);

    Page<Budget> getBudgets(PageRequest pageRequest, UserId userId);

    Optional<BudgetState> getBudgetState(BudgetId budgetId, UserId userId);
}
