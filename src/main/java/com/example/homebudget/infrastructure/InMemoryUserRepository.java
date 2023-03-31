package com.example.homebudget.infrastructure;

import com.example.homebudget.AppProfiles;
import com.example.homebudget.domain.users.BudgetAppUser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;


@Component
@Profile(AppProfiles.IN_MEMORY_SECURITY_SOURCE)
class InMemoryUserRepository implements UserRepository {
    private final Map<String, BudgetAppUser> storage = new HashMap<>();

    @Override
    public Optional<BudgetAppUser> findByName(String name) {
        return Optional.ofNullable(storage.get(name));
    }

    @Override
    public BudgetAppUser save(BudgetAppUser budgetAppUser) {
        storage.put(budgetAppUser.getUsername(), budgetAppUser);
        return budgetAppUser;
    }

    @Override
    public boolean existsByEmailOrName(String email, String name) {
        return storage.entrySet()
                .stream()
                .anyMatch(userAlreadyExistsPredicate(email, name));
    }

    private static Predicate<Map.Entry<String, BudgetAppUser>> userAlreadyExistsPredicate(String email, String name) {
        return entry -> Objects.equals(entry.getValue().email(), email) || Objects.equals(entry.getValue().name(), name);
    }
}
