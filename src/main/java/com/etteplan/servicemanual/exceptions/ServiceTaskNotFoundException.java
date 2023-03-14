package com.etteplan.servicemanual.exceptions;

public class ServiceTaskNotFoundException extends RuntimeException {
    public ServiceTaskNotFoundException(String message, Long id) {
        super(message + id);
    }
}
