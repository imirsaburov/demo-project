package com.example.demo.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private BigInteger balance;
}
