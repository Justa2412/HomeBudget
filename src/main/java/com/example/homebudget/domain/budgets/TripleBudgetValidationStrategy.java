package com.example.homebudget.domain.budgets;

import com.example.homebudget.domain.budgettypes.BudgetType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TripleBudgetValidationStrategy implements BudgetValidationStrategy {
    @Override
    public boolean supports(BudgetType budgetType) {
        return budgetType == BudgetType.CAPPED_TRIPLE;
    }

    @Override
    public boolean validateBudgetInBounds(BigDecimal max, BigDecimal current, BigDecimal requested) {
        BigDecimal calculatedMax = max.multiply(TRIPLE_BUDGET_MULTIPLIER);
        return calculatedMax.compareTo(current.add(requested)) >= 0;
    }

    private static final BigDecimal TRIPLE_BUDGET_MULTIPLIER = BigDecimal.valueOf(3);
}
