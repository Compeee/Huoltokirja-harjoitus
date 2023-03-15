package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.exceptions.ServiceTaskNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceTaskService {

    private final ServiceTaskRepository serviceTaskRepository;
    private final FactoryDeviceRepository factoryDeviceRepository;

    public ServiceTaskService(ServiceTaskRepository serviceTaskRepository, FactoryDeviceRepository factoryDeviceRepository) {
        this.serviceTaskRepository = serviceTaskRepository;
        this.factoryDeviceRepository = factoryDeviceRepository;
    }
    public List<ServiceTask> getAllServiceTasks() {
        return serviceTaskRepository.findAllByOrderByCategoryAscCreationDateDesc();
    }

    // Creates a service task, if no factory device exists with the given id throw a NOT_FOUND exception
    public ServiceTask createNewServiceTask(ServiceTask serviceTask, Long factoryDeviceId) {
        FactoryDevice factoryDevice = factoryDeviceRepository.findById(factoryDeviceId).orElseThrow(
                () -> new FactoryDeviceNotFoundException("Device not found with the given id: ", factoryDeviceId));
        serviceTask.setFactoryDevice(factoryDevice);
        serviceTask.setTaskState(TaskState.OPEN);
        serviceTask.setCreationDate(LocalDateTime.now());
        return serviceTaskRepository.save(serviceTask);
    }

    // Finds the service task by id and updates the state to closed
    // If no task found with given id throw a NOT_FOUND exception
    public String updateServiceTaskState(Long serviceTaskId) {
        ServiceTask serviceTask = serviceTaskRepository.findById(serviceTaskId).orElseThrow(
                () -> new ServiceTaskNotFoundException("Task not found with the given id: ", serviceTaskId));
        serviceTask.setTaskState(TaskState.CLOSED);
        serviceTaskRepository.save(serviceTask);
        return "Succesfully updated state of task id: " + serviceTaskId;
    }
    // Get all service tasks for a given factory device id
    // If no tasks are found with the given id throw an NOT_FOUND exception.
    public List<ServiceTask> getServiceTasksByFactoryDeviceId(Long factoryDeviceId) {
        List<ServiceTask> serviceTasks = serviceTaskRepository.findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc(factoryDeviceId);
        if(serviceTasks.isEmpty()){
            throw new ServiceTaskNotFoundException("No tasks found for device id: ", factoryDeviceId);
        }
        return serviceTasks;
    }

    // Deletes a service task by the given i
    // If a task is not found with the given id throw an NOT_FOUND exception.
    public String deleteServiceTaskById(Long serviceTaskId) {
        if(!serviceTaskRepository.existsById(serviceTaskId)){
            throw new ServiceTaskNotFoundException("No task exists with the given id: ", serviceTaskId);
        }
        serviceTaskRepository.deleteById(serviceTaskId);
        return "Succesfully deleted the task with id: " + serviceTaskId;
    }
}

