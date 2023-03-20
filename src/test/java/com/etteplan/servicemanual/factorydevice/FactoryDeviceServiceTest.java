package com.etteplan.servicemanual.factorydevice;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.exceptions.ServiceTaskNotFoundException;
import com.etteplan.servicemanual.servicetask.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FactoryDeviceServiceTest {


    @Mock
    private FactoryDeviceRepository factoryDeviceRepository;

    private FactoryDeviceService factoryDeviceService;

    @BeforeEach
    void setUp() {
        factoryDeviceService = new FactoryDeviceService(factoryDeviceRepository);
    }
    @Test
    void getAllFactoryDevices() {
        factoryDeviceService.getAllFactoryDevices();

        verify(factoryDeviceRepository).findAll();
    }
    @Test
    void getFactoryDeviceById() {
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");

        // Mock the repository's findById method to return task
        when(factoryDeviceRepository.findById(factoryDeviceId)).thenReturn(Optional.of(factoryDevice));

        // Call the service to get device by id
        FactoryDevice factoryDevice1 = factoryDeviceService.getFactoryDeviceById(factoryDeviceId).orElseThrow(
                () -> new FactoryDeviceNotFoundException("No device found with given id", factoryDeviceId));

        // Verify that the repository's findById method was called with the correct ID
        verify(factoryDeviceRepository).findById(factoryDeviceId);

        assertThat(factoryDevice1.getId()).isEqualTo(factoryDeviceId);
        assertThat(factoryDevice1.getName()).isEqualTo(factoryDevice.getName());
        assertThat(factoryDevice1.getType()).isEqualTo(factoryDevice.getType());

    }
    @Test
    void createNewFactoryDevice() {
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");

        // Mock the repository's save method to return task
        when(factoryDeviceRepository.save(factoryDevice)).thenReturn(factoryDevice);

        // Call the service to get create a new factory device
        FactoryDevice savedDevice = factoryDeviceService.createNewFactoryDevice(factoryDevice);

        // Verify that the repository's save method was called with the correct device
        verify(factoryDeviceRepository).save(factoryDevice);

        assertThat(savedDevice.getId()).isEqualTo(factoryDeviceId);
        assertThat(savedDevice.getName()).isEqualTo(factoryDevice.getName());
        assertThat(savedDevice.getType()).isEqualTo(factoryDevice.getType());

    }
}
