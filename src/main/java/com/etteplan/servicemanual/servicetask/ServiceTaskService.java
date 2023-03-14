package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.exceptions.ServiceTaskNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
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
        return serviceTaskRepository.findAll();
    }

    public ServiceTask createNewServiceTask(ServiceTask serviceTask, Long factoryDeviceId) {
        FactoryDevice factoryDevice = factoryDeviceRepository.findById(factoryDeviceId).orElseThrow(
                () -> new FactoryDeviceNotFoundException("Device not found with the given id:", factoryDeviceId));
        serviceTask.setFactoryDevice(factoryDevice);
        serviceTask.setTaskState(TaskState.OPEN);
        serviceTask.setTaskRegisterDate(LocalDateTime.now());
        return serviceTaskRepository.save(serviceTask);
    }

    public String updateServiceTaskState(Long serviceTaskId) {
        // Finds the service and updates the state to closed
        ServiceTask serviceTask = serviceTaskRepository.findById(serviceTaskId).orElseThrow(
                () -> new ServiceTaskNotFoundException("Task not found with the given id: ", serviceTaskId));
        serviceTask.setTaskState(TaskState.CLOSED);
        serviceTaskRepository.save(serviceTask);
        return "Succesfully updated state of task id: " + serviceTaskId;
    }

    public List<ServiceTask> getServiceTasksByFactoryDeviceId(Long factoryDeviceId) {
       return serviceTaskRepository.findTasksByFactoryDeviceId(factoryDeviceId);
    }

    public String deleteServiceTaskById(Long serviceTaskId) {
        if(!serviceTaskRepository.existsById(serviceTaskId)){
            throw new ServiceTaskNotFoundException("No task exists with the given id: ", serviceTaskId);
        }
        serviceTaskRepository.deleteById(serviceTaskId);
        return "Succesfully deleted the task with id: " + serviceTaskId;
    }
}
