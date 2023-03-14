package com.etteplan.servicemanual.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
    @ResponseBody
    @ExceptionHandler(FactoryDeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String FactoryDeviceNotFoundHandler(FactoryDeviceNotFoundException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(ServiceTaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ServiceTaskNotFoundHandler(ServiceTaskNotFoundException ex) {
        return ex.getMessage();
    }

}

