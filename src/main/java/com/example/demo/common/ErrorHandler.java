package com.example.demo.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice("com.example")
public class ErrorHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e, HttpServletRequest request) {
        ErrorResponse build = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(e.getStatus().value())
                .error(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getStatus())
                .body(build);
    }
}
