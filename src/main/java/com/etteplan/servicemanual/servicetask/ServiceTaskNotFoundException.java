package com.etteplan.servicemanual.servicetask;

public class ServiceTaskNotFoundException extends RuntimeException {
    public ServiceTaskNotFoundException(Long id) {
        super("Could not find a service task with id: " + id);
    }
}
