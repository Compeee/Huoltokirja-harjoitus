package com.etteplan.servicemanual;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;

@SpringBootApplication
public class ServiceManualApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ServiceManualApplication.class, args);
    }

}