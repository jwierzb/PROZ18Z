package com.proz2018;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DevicesNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DevicesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String devicesNotFoundHandler(DevicesNotFoundException ex) {
        return ex.getMessage();
    }
}