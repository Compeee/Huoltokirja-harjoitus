package com.etteplan.servicemanual.servicetask;

import static org.assertj.core.api.Assertions.assertThat;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
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
    ServiceTask serviceTask1;
    ServiceTask serviceTask2;
    ServiceTask serviceTask3;
    FactoryDevice factoryDevice = new FactoryDevice("Device 1", 1999, "Type 5");

    @BeforeEach
    void setUp(){
        serviceTask1 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.MILD);
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
        List<ServiceTask> listOfTasks = serviceTaskRepository.findAllByOrderByCategoryAscCreationDateDesc();
        assertThat(listOfTasks.get(0)).isEqualTo(serviceTask3);
        assertThat(listOfTasks.get(0).getCategory()).isEqualTo(serviceTask3.getCategory());
        ServiceTask serviceTaskNew = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        serviceTaskRepository.save(serviceTaskNew);
        List<ServiceTask> updatedList = serviceTaskRepository.findAllByOrderByCategoryAscCreationDateDesc();
        assertThat(updatedList.get(0)).isEqualTo(serviceTaskNew);
    }

    @Test
    void findByDeviceIdAndOrderByCategoryAndCreationDate(){
        List<ServiceTask> listOfTasks = serviceTaskRepository.findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc(factoryDevice.getId());
        assertThat(listOfTasks.get(0).getCategory()).isEqualTo(serviceTask3.getCategory());
    }
}
