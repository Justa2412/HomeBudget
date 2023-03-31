package com.example.homebudget.domain.budgets;

import com.example.homebudget.domain.budgettypes.BudgetType;
import com.example.homebudget.domain.expenses.Expense;
import com.example.homebudget.domain.users.UserId;
import com.example.homebudget.infrastructure.BudgetRepository;
import com.example.homebudget.infrastructure.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


@Service
class DefaultBudgetService implements BudgetService {

    private final BudgetRepository repository;

    private final ExpenseRepository expenseRepository;

    private final Supplier<BudgetId> budgetIdSupplier;


    public DefaultBudgetService(BudgetRepository repository,
                                ExpenseRepository expenseRepository, Supplier<BudgetId> budgetIdSupplier
    ) {
        this.repository = repository;
        this.expenseRepository = expenseRepository;
        this.budgetIdSupplier = budgetIdSupplier;
    }

    @Override

    public Budget registerNewBudget(String name, UserId userId, BigDecimal cap, BudgetType budgetType) {
        Budget budget = new Budget(budgetIdSupplier.get(), userId, name, cap, budgetType);
        return repository.save(budget);
    }

    @Override

    public Optional<Budget> getBudget(BudgetId budgetId, UserId userId) {
        return repository.findBudgetByBudgetIdAndUserId(budgetId, userId);
    }

    @Override

    public Optional<Budget> updateBudget(BudgetId budgetId, String name, UserId userId, BigDecimal cap, BudgetType budgetType) {

        Budget updatedBudget = new Budget(budgetId, userId, name, cap, budgetType);
        return Optional.of(

                repository.findBudgetByBudgetIdAndUserId(budgetId, userId)

                        .map(repoBudget -> repository.save(updatedBudget))

                        .orElseGet(() -> repository.insert(updatedBudget))
        );
    }

    @Override
    public void deleteBudget(BudgetId budgetId, UserId userId) {

        repository.deleteBudgetByBudgetIdAndUserId(budgetId, userId);
    }

    @Override

    public Page<Budget> getBudgets(PageRequest pageRequest, UserId userId) {
        return repository.getBudgetsByUserId(userId, pageRequest);
    }

    @Override
    public Optional<BudgetState> getBudgetState(BudgetId budgetId, UserId userId) {

        Optional<Budget> budget = repository.findBudgetByBudgetIdAndUserId(budgetId, userId);


        Optional<List<Expense>> expenses = budget
                .map(Budget::budgetId)
                .map(id -> expenseRepository.findExpensesByUserIdAndBudgetId(userId, id));


        Integer totalExpensesFound = calculateCountOf(expenses);


        BigDecimal totalExpensesSum = calculateSumOf(expenses);


        return budget.map(foundBudget -> new BudgetState(
                        foundBudget.budgetId(),
                        foundBudget.maxAmount(),
                        totalExpensesFound,
                        totalExpensesSum,
                        calculateAmountLeft(foundBudget.maxAmount(), totalExpensesSum)
                )
        );
    }


    private static Integer calculateCountOf(Optional<List<Expense>> expenses) {
        return expenses.map(List::size).orElse(NO_EXPENSES_COUNT);
    }


    private static BigDecimal calculateSumOf(Optional<List<Expense>> expenses) {
        return expenses.map(foundExpenses -> foundExpenses.stream()
                        .map(Expense::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                )
                .orElse(NO_EXPENSES_SUM);
    }


    private BigDecimal calculateAmountLeft(BigDecimal max, BigDecimal currentState) {
        return max.subtract(currentState);
    }

    private static final Integer NO_EXPENSES_COUNT = 0;
    private static final BigDecimal NO_EXPENSES_SUM = BigDecimal.ZERO;
}