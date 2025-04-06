package com.example.demo.user;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserMockService {
    private final UserService userService;

    @Transactional
    public void loadMockUsers() {
        if (userService.countUsers() > 0)
            return;

        Faker faker = new Faker();

        for (int i = 0; i < 15; i++) {
            Name name = faker.name();
            CreateUserRequest createUserRequest = new CreateUserRequest(name.firstName(), name.lastName());
            userService.create(createUserRequest);
        }
    }
}
