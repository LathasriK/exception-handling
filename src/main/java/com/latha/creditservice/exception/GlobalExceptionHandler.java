package com.latha.creditservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

        //    handle specific
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),404, exception.getMessage(), "BadRequest");
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }


        //     handle global
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),500, exception.getMessage(),"Internal server error");
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

        //    handle specific
    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<?> handleAPIException(EmptyInputException exception) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), 400, exception.getMessage(), "BadRequest");

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //    handle specific
//    @ExceptionHandler(SuccessException.class)
//    public ResponseEntity<?> handleAPIException(SuccessException exception, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), 200);
//        return new ResponseEntity(errorDetails, HttpStatus.OK);
    }


