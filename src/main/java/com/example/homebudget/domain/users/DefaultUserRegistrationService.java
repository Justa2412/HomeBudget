package com.example.homebudget.domain.users;

import com.example.homebudget.infrastructure.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;


@Service
class DefaultUserRegistrationService implements UserRegistrationService {

    private final UserRepository repository;

    private final Supplier<UserId> userIdSupplier;

    private final PasswordEncoder encoder;


    public DefaultUserRegistrationService(UserRepository repository, Supplier<UserId> userIdSupplier, PasswordEncoder encoder) {
        this.repository = repository;
        this.userIdSupplier = userIdSupplier;
        this.encoder = encoder;
    }


    @Override

    public BudgetAppUser registerNewUser(String userName, String rawPassword, String email, List<String> roles) {

        if (repository.existsByEmailOrName(email, userName)) {

            throw new UnableToRegisterException();
        }


        BudgetAppUser budgetAppUser = new BudgetAppUser(userIdSupplier.get(), userName, email, encoder.encode(rawPassword), roles, true);


        return repository.save(budgetAppUser);
    }
}
