package com.example.homebudget.domain.budgettypes;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
class DefaultBudgetTypesService implements BudgetTypesService {

    @Override
    public List<BudgetType> allBudgetTypes() {
        return List.of(BudgetType.values());
    }
}
