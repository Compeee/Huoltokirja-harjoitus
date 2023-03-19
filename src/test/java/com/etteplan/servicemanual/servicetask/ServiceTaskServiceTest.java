package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTaskServiceTest {

    @Mock
    private ServiceTaskRepository serviceTaskRepository;
    @Mock
    private FactoryDeviceRepository factoryDeviceRepository;

    private ServiceTaskService serviceTaskService;
    private FactoryDeviceService factoryDeviceService;


    @BeforeEach
    void setUp() {
        serviceTaskService = new ServiceTaskService(serviceTaskRepository, factoryDeviceRepository);
        factoryDeviceService = new FactoryDeviceService(factoryDeviceRepository);
    }

    @Test
    void CanGetAllServiceTasks() {
        serviceTaskService.getAllServiceTasks();

        verify(serviceTaskRepository).findAllByOrderByCategoryAscCreationDateDesc();
    }

    @Test
    void CanCreateNewServiceTask() {
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");
        doReturn(Optional.of(factoryDevice)).when(factoryDeviceRepository).findById(factoryDeviceId);
        FactoryDevice factoryDeviceSaved = factoryDeviceService.getFactoryDeviceById(factoryDeviceId)
                .orElseThrow(() -> new FactoryDeviceNotFoundException("No device exists with given id: ", factoryDeviceId));

        serviceTaskService.createNewServiceTask(factoryDeviceId, TaskCategory.CRITICAL, "Broken");

        ArgumentCaptor<ServiceTask> serviceTaskArgumentCaptor = ArgumentCaptor.forClass(ServiceTask.class);

        verify(serviceTaskRepository).save(serviceTaskArgumentCaptor.capture());

        ServiceTask capturedTask = serviceTaskArgumentCaptor.getValue();

        assertEquals(factoryDeviceSaved.getId(), factoryDeviceId);
        assertThat(capturedTask.getDescription()).isEqualTo("Broken");
        assertThat(capturedTask.getCategory()).isEqualTo(TaskCategory.CRITICAL);
        assertThat(capturedTask.getFactoryDevice()).isEqualTo(factoryDevice);
    }

    @Disabled
    @Test
    void updateServiceTaskState() {
    }

    @Test
    void getServiceTasksByFactoryDeviceId() {
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");

        ServiceTask task1 = new ServiceTask(1L, LocalDateTime.now(), "Broken", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        ServiceTask task2 = new ServiceTask(2L, LocalDateTime.now(), "Needs maintenance", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT);

        List<ServiceTask> tasks = Arrays.asList(task1, task2);

        doReturn(tasks).when(serviceTaskRepository).findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc(factoryDeviceId);

        List<ServiceTask> tasksByDeviceId = serviceTaskService.getServiceTasksByFactoryDeviceId(factoryDeviceId);

        ServiceTask firstTask = tasksByDeviceId.get(0);
        assertThat(firstTask.getDescription()).isEqualTo("Broken");
        assertThat(firstTask.getCategory()).isEqualTo(TaskCategory.CRITICAL);

        ServiceTask secondTask = tasksByDeviceId.get(1);
        assertThat(secondTask.getDescription()).isEqualTo("Needs maintenance");
        assertThat(secondTask.getCategory()).isEqualTo(TaskCategory.UNIMPORTANT);
    }

    @Disabled
    @Test
    void deleteServiceTaskById() {


    }
}