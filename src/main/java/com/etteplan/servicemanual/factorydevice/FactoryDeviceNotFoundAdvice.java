package com.etteplan.servicemanual.factorydevice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FactoryDeviceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FactoryDeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String factoryDeviceNotFoundHandler(FactoryDeviceNotFoundException ex) {
        return ex.getMessage();
    }
}