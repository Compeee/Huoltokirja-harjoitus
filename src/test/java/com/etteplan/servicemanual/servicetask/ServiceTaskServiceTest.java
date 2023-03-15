package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTaskServiceTest {
    @Mock
    private ServiceTaskRepository serviceTaskRepository;
    @Mock
    private FactoryDeviceRepository factoryDeviceRepository;
    private ServiceTaskService serviceTaskService;

    @BeforeEach
    void setUp(){
        serviceTaskService = new ServiceTaskService(serviceTaskRepository, factoryDeviceRepository);
    }
    @Test
    void canGetAllServiceTasks(){
        serviceTaskService.getAllServiceTasks();
        verify(serviceTaskRepository).findAllByOrderByCategoryAscCreationDateDesc();
    }

    @Test
    void createNewServiceTask() {
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setTaskState(TaskState.OPEN);
        Long factoryDeviceId = 1L;

        FactoryDevice factoryDevice = new FactoryDevice();
        factoryDevice.setId(factoryDeviceId);

        when(factoryDeviceRepository.findById(factoryDeviceId)).thenReturn(Optional.of(factoryDevice));

        when(serviceTaskRepository.save(serviceTask)).thenReturn(serviceTask);

        ServiceTask result = serviceTaskService.createNewServiceTask(serviceTask, factoryDeviceId);

        assertNotNull(result);
        assertEquals(serviceTask, result);
        assertEquals(factoryDevice, serviceTask.getFactoryDevice());
        assertEquals(TaskState.OPEN, serviceTask.getTaskState());


        verify(factoryDeviceRepository).findById(factoryDeviceId);
        verify(serviceTaskRepository).save(serviceTask);
    }
    @Test
    void createNewServiceTaskFactoryDeviceNotFound() {
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setTaskState(TaskState.OPEN);
        Long factoryDeviceId = 1L;

        when(factoryDeviceRepository.findById(factoryDeviceId)).thenReturn(Optional.empty());

        FactoryDeviceNotFoundException exception = assertThrows(FactoryDeviceNotFoundException.class,
                () -> serviceTaskService.createNewServiceTask(serviceTask, factoryDeviceId));

        assertEquals("Device not found with the given id: " + factoryDeviceId, exception.getMessage());

        verify(factoryDeviceRepository).findById(factoryDeviceId);

    }
}
