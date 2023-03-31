package com.example.homebudget.domain.budgets;

import java.math.BigDecimal;

public record BudgetState(
        BudgetId budgetId,
        BigDecimal cap,
        Integer totalExpenses,
        BigDecimal expensesSum,
        BigDecimal amountLeft
) {
}

