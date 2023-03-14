package com.etteplan.servicemanual.servicetask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class ServiceTaskController {
    private final ServiceTaskService serviceTaskService;

    public ServiceTaskController(ServiceTaskService serviceTaskService) {
        this.serviceTaskService = serviceTaskService;
    }

    @GetMapping
    public List<ServiceTask> getAllServiceTasks(){
        return serviceTaskService.getAllServiceTasks();
    }

    @PostMapping("/{factoryDeviceId}")
    public ServiceTask createNewServiceTask(@RequestBody ServiceTask serviceTask, @PathVariable("factoryDeviceId") Long factoryDeviceId){
       return serviceTaskService.createNewServiceTask(serviceTask, factoryDeviceId);
    }

    @PatchMapping("/{serviceTaskId}")
    public String updateServiceTaskState(@PathVariable("serviceTaskId") Long serviceTaskId){
        return serviceTaskService.updateServiceTaskState(serviceTaskId);
    }

    @GetMapping("/device/{factoryDeviceId}")
    public List<ServiceTask> getServiceTasksByFactoryDeviceId(@PathVariable("factoryDeviceId") Long factoryDeviceId){
        return serviceTaskService.getServiceTasksByFactoryDeviceId(factoryDeviceId);
    }

    @DeleteMapping("/{serviceTaskId}")
    public String deleteServiceTaskById(@PathVariable("serviceTaskId") Long serviceTaskId){
        return serviceTaskService.deleteServiceTaskById(serviceTaskId);
    }

}
