package com.example.todolistapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ProjectControllerAdvice  {

    @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<String> handleNoSuchElementException(){
            return new ResponseEntity<>("No such value is present in database. Please change your request.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(){
        return new ResponseEntity<>("The value cannot be null. Please change your request.", HttpStatus.NOT_ACCEPTABLE);
    }


}
