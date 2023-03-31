package com.example.homebudget.domain.expenses;

import com.example.homebudget.domain.budgets.Budget;
import com.example.homebudget.domain.budgets.BudgetId;
import com.example.homebudget.domain.budgets.BudgetValidationStrategy;
import com.example.homebudget.domain.users.UserId;
import com.example.homebudget.infrastructure.BudgetRepository;
import com.example.homebudget.infrastructure.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class DefaultExpensesService implements ExpensesService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final Supplier<ExpenseId> expenseIdSupplier;
    private final Set<BudgetValidationStrategy> validationStrategies;

    public DefaultExpensesService(
            ExpenseRepository expenseRepository,
            BudgetRepository budgetRepository,
            Supplier<ExpenseId> expenseIdSupplier,
            Set<BudgetValidationStrategy> validationStrategies
    ) {
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
        this.expenseIdSupplier = expenseIdSupplier;
        this.validationStrategies = validationStrategies;
    }

    @Override
    public Expense registerNewExpense(String title, BigDecimal amount, Instant registrationTimestamp, UserId userId, BudgetId budgetId) {
        return budgetRepository.findBudgetByBudgetIdAndUserId(budgetId, userId)
                .map(budget -> isWithinBounds(budget, amount))
                .filter(isWithinBounds -> isWithinBounds)
                .map(isWithinBounds -> doRegisterNewExpense(title, amount, registrationTimestamp, userId, budgetId))
                .orElseThrow(() -> new IllegalStateException("Can't register new budget"));
    }

    private Expense doRegisterNewExpense(String title, BigDecimal amount, Instant registrationTimestamp, UserId userId, BudgetId budgetId) {
        return expenseRepository.save(Expense.of(expenseIdSupplier.get(), title, amount, userId, budgetId, registrationTimestamp));
    }

    private boolean isWithinBounds(Budget budget, BigDecimal requestedAmount) {
        BigDecimal max = budget.maxAmount();

        BigDecimal currentExpenses = calculateCurrentExpensesSum(budget);

        return validationStrategies.stream()
                .filter(strategy -> strategy.supports(budget.type()))
                .findFirst()
                .map(budgetValidationStrategy -> budgetValidationStrategy.validateBudgetInBounds(max, currentExpenses, requestedAmount))
                .orElseThrow(() -> new IllegalStateException("Unsupported budget type" + budget.type()));
    }

    private BigDecimal calculateCurrentExpensesSum(Budget budget) {
        return expenseRepository.findExpensesByUserIdAndBudgetId(budget.userId(), budget.budgetId())
                .stream()
                .map(Expense::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Optional<Expense> getExpenseById(ExpenseId expenseId, UserId userId) {
        return expenseRepository.findExpenseByExpenseIdAndUserId(expenseId, userId);
    }

    @Override
    public Page<Expense> getExpensesPage(PageRequest pageRequest, UserId userId) {
        return expenseRepository.findExpensesByUserId(userId, pageRequest);
    }
}
