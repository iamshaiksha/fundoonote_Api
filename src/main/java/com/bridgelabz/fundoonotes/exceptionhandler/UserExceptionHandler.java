package com.bridgelabz.fundoonotes.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotes.exception.UserException;

import org.springframework.http.HttpStatus;


@ControllerAdvice //to handle the exceptions globally.
public class UserExceptionHandler{
	
   @ExceptionHandler(value = UserException.class)//used to handle the specific exceptions and sending the custom responses to the client.
   public ResponseEntity<Object> handleUserNotFoundException(UserException userNotFoundException) {
	   
      return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
   }
}