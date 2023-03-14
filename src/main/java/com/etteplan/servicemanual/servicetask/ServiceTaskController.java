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
    public ServiceTask createNewServiceTask(@RequestBody ServiceTask serviceTask, @PathVariable Long factoryDeviceId){
       return serviceTaskService.createNewServiceTask(serviceTask, factoryDeviceId);
    }

    @PatchMapping("/{serviceTaskId}")
    public void updateServiceTaskState(@PathVariable Long serviceTaskId){
        serviceTaskService.updateServiceTaskState(serviceTaskId);
    }

}
