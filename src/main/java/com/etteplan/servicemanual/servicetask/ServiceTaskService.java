package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceNotFoundException;
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
        serviceTask.setTaskState(TaskState.OPEN);
        serviceTask.setTaskRegisterDate(LocalDateTime.now());
        FactoryDevice factoryDevice = factoryDeviceRepository.findById(factoryDeviceId).orElseThrow(() -> new FactoryDeviceNotFoundException(factoryDeviceId));
        serviceTask.setFactoryDevice(factoryDevice);
        return serviceTaskRepository.save(serviceTask);
    }

    public void updateServiceTaskState(Long serviceTaskId) {
        ServiceTask serviceTask = serviceTaskRepository.findById(serviceTaskId).orElseThrow();
        serviceTask.setTaskState(TaskState.SERVICE_COMPLETE);
        serviceTaskRepository.save(serviceTask);
    }
}
