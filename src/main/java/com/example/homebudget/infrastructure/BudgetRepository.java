package com.example.homebudget.infrastructure;

import com.example.homebudget.domain.budgets.Budget;
import com.example.homebudget.domain.budgets.BudgetId;
import com.example.homebudget.domain.users.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BudgetRepository extends MongoRepository<Budget, BudgetId> {
    Optional<Budget> findBudgetByBudgetIdAndUserId(BudgetId budgetId, UserId userId);

    Page<Budget> getBudgetsByUserId(UserId userId, Pageable pageable);

    void deleteBudgetByBudgetIdAndUserId(BudgetId budgetId, UserId userId);
}
