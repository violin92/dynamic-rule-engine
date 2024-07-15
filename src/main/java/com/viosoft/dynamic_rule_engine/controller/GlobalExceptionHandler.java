package com.viosoft.dynamic_rule_engine.controller;

import com.viosoft.dynamic_rule_engine.exception.InvalidScriptException;
import com.viosoft.dynamic_rule_engine.exception.RuleExecutionException;
import com.viosoft.dynamic_rule_engine.exception.RuleNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuleNotFoundException.class)
    public ResponseEntity<String> handleRuleNotFoundException(RuleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuleExecutionException.class)
    public ResponseEntity<String> handleConstraintViolationException(RuleExecutionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidScriptException.class)
    public ResponseEntity<String> handleConstraintViolationException(InvalidScriptException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
