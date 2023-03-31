package com.example.homebudget.domain.budgets;

import com.example.homebudget.domain.budgettypes.BudgetType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class DoubleCappedBudgetValidationStrategy implements BudgetValidationStrategy {
    @Override
    public boolean supports(BudgetType budgetType) {
        return budgetType == BudgetType.CAPPED_DOUBLE;
    }

    @Override
    public boolean validateBudgetInBounds(BigDecimal max, BigDecimal current, BigDecimal requested) {
        BigDecimal calculatedMax = max.multiply(DOUBLE_BUDGET_MULTIPLIER);
        return calculatedMax.compareTo(current.add(requested)) >= 0;
    }

    private static final BigDecimal DOUBLE_BUDGET_MULTIPLIER = BigDecimal.valueOf(2);
}
