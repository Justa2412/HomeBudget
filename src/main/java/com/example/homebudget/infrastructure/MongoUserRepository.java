package com.example.homebudget.infrastructure;

import com.example.homebudget.AppProfiles;
import com.example.homebudget.domain.users.BudgetAppUser;
import com.example.homebudget.domain.users.UserId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile(AppProfiles.MONGO_SECURITY_SOURCE)
interface MongoUserRepository extends MongoRepository<BudgetAppUser, UserId>, UserRepository{
}
