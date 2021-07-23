package com.example.demo.error;

import com.example.demo.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class, Exception.class})
    public ResponseEntity<Object> exceptionHandler(Exception ex, WebRequest request) {
        System.out.println("ExceptionHandler.exceptionHandler");
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        ex.printStackTrace();
        return super.handleExceptionInternal(ex, error, null, HttpStatus.BAD_REQUEST, request);
    }
}
