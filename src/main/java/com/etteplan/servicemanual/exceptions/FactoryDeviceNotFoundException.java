package com.etteplan.servicemanual.exceptions;




public class FactoryDeviceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public FactoryDeviceNotFoundException(String message, Long id) {
        super(message + id);
    }
}