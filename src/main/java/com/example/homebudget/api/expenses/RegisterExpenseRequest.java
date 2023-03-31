package com.example.homebudget.api.expenses;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record RegisterExpenseRequest(
        @NotNull(message = "Expense title cannot be null")
        @NotEmpty(message = "Title cannot be null")


        String title,
        @NotNull(message = "Expense amount cannot be null")
        @Positive(message = "Expense amount cannot be negative or zero")
        BigDecimal amount,
        @NotNull(message = "BudgetId cannot be null")
        @NotBlank(message = "BudgetId cannot be blank")
        String budgetId
) {
}
