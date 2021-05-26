package com.accenture.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
    log.error(ex.getMessage(), ex);
    return new ErrorResponse(ex.getMessage());
  }

}
