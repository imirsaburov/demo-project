package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

    @PutMapping("{id}")
    public UserDTO edit(@PathVariable("id") Long id, @Valid @RequestBody EditUserRequest createUserRequest) {
        return userService.edit(id, createUserRequest);
    }

    @GetMapping("/pageable")
    public PagedModel<UserDTO> pageable(Pageable pageable) {
        return userService.findAll(pageable);
    }
}
