package com.example.homebudget.infrastructure;

import com.example.homebudget.domain.budgets.BudgetId;
import com.example.homebudget.domain.expenses.Expense;
import com.example.homebudget.domain.expenses.ExpenseId;
import com.example.homebudget.domain.users.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends MongoRepository<Expense, ExpenseId> {
    Optional<Expense> findExpenseByExpenseIdAndUserId(ExpenseId expenseId, UserId userId);

    Page<Expense> findExpensesByUserId(UserId userId, Pageable pageable);

    List<Expense> findExpensesByUserIdAndBudgetId(UserId userId, BudgetId budgetId);
}
