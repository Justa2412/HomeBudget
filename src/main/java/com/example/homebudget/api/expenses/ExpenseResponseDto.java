package com.example.homebudget.api.expenses;

import com.example.homebudget.domain.expenses.Expense;

import java.math.BigDecimal;

public record ExpenseResponseDto(
        String title,
        String expenseId,
        BigDecimal amount
) {
    public static ExpenseResponseDto fromDomain(Expense expense) {
        return new ExpenseResponseDto(
                expense.title(),
                expense.expenseId().value(),
                expense.amount()
        );
    }
}
