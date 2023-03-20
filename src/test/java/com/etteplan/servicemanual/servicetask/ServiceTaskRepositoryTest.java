package com.etteplan.servicemanual.servicetask;

import static org.assertj.core.api.Assertions.assertThat;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ServiceTaskRepositoryTest {
    @Autowired
    private ServiceTaskRepository serviceTaskRepository;
    @Autowired
    private FactoryDeviceRepository factoryDeviceRepository;
    ServiceTask serviceTask1;
    ServiceTask serviceTask2;
    ServiceTask serviceTask3;
    FactoryDevice factoryDevice = new FactoryDevice("Device 1", 1999, "Type 5");

    @BeforeEach
    void setUp(){
        factoryDeviceRepository.save(factoryDevice);
        serviceTask1 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT);
        serviceTask2 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.IMPORTANT);
        serviceTask3 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        serviceTaskRepository.save(serviceTask1);
        serviceTaskRepository.save(serviceTask2);
        serviceTaskRepository.save(serviceTask3);
    }

    @AfterEach
    void tearDown(){
        serviceTask1 = null;
        serviceTask2 = null;
        serviceTask3 = null;
        serviceTaskRepository.deleteAll();
    }

    @Test
    void testFindAllAndOrderByCategoryAndCreationDate(){
        // Retrieve a list of tasks from the database
        List<ServiceTask> listOfTasks = serviceTaskRepository.findAllByOrderByCategoryAscCreationDateDesc();

        // Make sure that the task with category CRITICAL is first on teh list
        assertThat(listOfTasks.get(0)).isEqualTo(serviceTask3);
        assertThat(listOfTasks.get(0).getCategory()).isEqualTo(serviceTask3.getCategory());
        // Add a new task with CRITICAL category
        ServiceTask serviceTaskNew = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        serviceTaskRepository.save(serviceTaskNew);
        // Retrieve the updated list and make sure the new task is displayed first because it was created after the first
        List<ServiceTask> updatedList = serviceTaskRepository.findAllByOrderByCategoryAscCreationDateDesc();
        assertThat(updatedList.get(0)).isEqualTo(serviceTaskNew);
    }

    @Test
    void findByDeviceIdAndOrderByCategoryAndCreationDate(){
        // Retrieve tasks by device id and make sure that the CRITICAL task is first in the list
        List<ServiceTask> listOfTasks = serviceTaskRepository.findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc(factoryDevice.getId());
        assertThat(listOfTasks.get(0).getCategory()).isEqualTo(serviceTask3.getCategory());
    }
}
