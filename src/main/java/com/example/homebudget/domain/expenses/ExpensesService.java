package com.example.homebudget.domain.expenses;

import com.example.homebudget.domain.budgets.BudgetId;
import com.example.homebudget.domain.users.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface ExpensesService {
    Expense registerNewExpense(String title, BigDecimal amount, Instant registrationTimestamp, UserId userId, BudgetId budgetId);

    Optional<Expense> getExpenseById(ExpenseId expenseId, UserId userId);

    Page<Expense> getExpensesPage(PageRequest pageRequest, UserId userId);
}
