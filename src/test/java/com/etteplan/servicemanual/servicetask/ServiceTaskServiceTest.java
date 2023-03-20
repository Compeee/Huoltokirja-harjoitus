package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.exceptions.ServiceTaskNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceService;
import org.hibernate.service.Service;
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


    @Test
    void updateServiceTaskState() {
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");

        ServiceTask task1 = new ServiceTask(1L, LocalDateTime.now(), "Broken", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);

        // mock the repository to return the service task object
        when(serviceTaskRepository.findById(task1.getId()))
                .thenReturn(Optional.of(task1));

        // call the service to update the state of the service task
        serviceTaskService.updateServiceTaskState(task1.getId());

        // verify that the service task state was updated
        assertEquals(TaskState.CLOSED, task1.getTaskState());

        // verify that the repository's save method was called
        verify(serviceTaskRepository).save(task1);
    }

    @Test
    void getServiceTasksByFactoryDeviceId() {
        // Create a sample factory device and service tasks associated with it
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");
        ServiceTask task1 = new ServiceTask(1L, LocalDateTime.now(), "Broken", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        ServiceTask task2 = new ServiceTask(2L, LocalDateTime.now(), "Needs maintenance", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT);
        List<ServiceTask> tasks = Arrays.asList(task1, task2);

        // Mock the repository's findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc method to return the sample tasks
        doReturn(tasks).when(serviceTaskRepository).findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc(factoryDeviceId);

        // Call the service to get the service tasks associated with the sample factory device
        List<ServiceTask> tasksByDeviceId = serviceTaskService.getServiceTasksByFactoryDeviceId(factoryDeviceId);

        // Assert that the returned tasks match the expected tasks
        ServiceTask firstTask = tasksByDeviceId.get(0);
        assertThat(firstTask.getDescription()).isEqualTo("Broken");
        assertThat(firstTask.getCategory()).isEqualTo(TaskCategory.CRITICAL);

        ServiceTask secondTask = tasksByDeviceId.get(1);
        assertThat(secondTask.getDescription()).isEqualTo("Needs maintenance");
        assertThat(secondTask.getCategory()).isEqualTo(TaskCategory.UNIMPORTANT);
    }

    @Test
    void deleteServiceTaskById() {
        Long serviceTaskId = 1L;

        // Mock the repository's existsById method to return true
        when(serviceTaskRepository.existsById(serviceTaskId)).thenReturn(true);

        // Call the service to delete the service task
        String result = serviceTaskService.deleteServiceTaskById(serviceTaskId);

        // Verify that the repository's deleteById method was called with the correct ID
        verify(serviceTaskRepository).deleteById(serviceTaskId);

        // Verify that the service returns the correct success message
        assertEquals("Succesfully deleted the task with id: " + serviceTaskId, result);


    }
    @Test
    void deleteServiceTaskByIdTaskDoesNotExist() {
        Long serviceTaskId = 1L;

        // Mock the repository's existsById method to return true
        when(serviceTaskRepository.existsById(serviceTaskId)).thenReturn(false);

        // Call the service to delete the service task
        Exception exception = assertThrows(ServiceTaskNotFoundException.class, () -> {
            serviceTaskService.deleteServiceTaskById(serviceTaskId);
        });

        String expectedErrorMessage = "No task exists with the given id: " + serviceTaskId;
        String actualErrorMessage = exception.getMessage();
        assertTrue(actualErrorMessage.contains(expectedErrorMessage));


    }
    @Test
    void getServiceTaskById() {
        Long serviceTaskId = 1L;
        Long factoryDeviceId = 1L;
        FactoryDevice factoryDevice = new FactoryDevice(factoryDeviceId, "Device 1", 1999, "Type 5");
        ServiceTask task = new ServiceTask(serviceTaskId, LocalDateTime.now(), "Broken", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        // Mock the repository's findById method to return task
        when(serviceTaskRepository.findById(serviceTaskId)).thenReturn(Optional.of(task));

        // Call the service to get service task by id
        ServiceTask task1 = serviceTaskService.getServiceTaskById(serviceTaskId).orElseThrow(
                () -> new ServiceTaskNotFoundException("No task found with given id", serviceTaskId));

        // Verify that the repository's deleteById method was called with the correct ID
        verify(serviceTaskRepository).findById(serviceTaskId);

        assertThat(task1.getId()).isEqualTo(serviceTaskId);
        assertThat(task1.getDescription()).isEqualTo(task.getDescription());
        assertThat(task1.getCategory()).isEqualTo(task.getCategory());

    }
}