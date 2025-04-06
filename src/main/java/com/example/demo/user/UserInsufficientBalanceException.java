package com.example.demo.user;

import com.example.demo.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserInsufficientBalanceException extends BaseException {
    public UserInsufficientBalanceException() {
        super(HttpStatus.BAD_REQUEST, "user is insufficient balance");
    }
}
