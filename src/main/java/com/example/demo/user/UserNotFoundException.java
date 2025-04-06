package com.example.demo.user;

import com.example.demo.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "User not found following id: " + id);
    }
}
