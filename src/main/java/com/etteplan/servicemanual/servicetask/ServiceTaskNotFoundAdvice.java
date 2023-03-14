package com.etteplan.servicemanual.servicetask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServiceTaskNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ServiceTaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ServiceTaskNotFoundHandler(ServiceTaskNotFoundException ex) {
        return ex.getMessage();
    }
}
