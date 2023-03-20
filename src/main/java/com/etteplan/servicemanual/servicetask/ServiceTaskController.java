package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.exceptions.ServiceTaskNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Returns a list of all the service tasks in the database, ordered by category firstly and secondly by creation date")
    @GetMapping
    public List<ServiceTask> getAllServiceTasks(){
        return serviceTaskService.getAllServiceTasks();
    }

    @Operation(summary = "Creates a new service task, accepts a createNewTaskRequest which includes a device id, task category and a description for the task, valid categories are IMPORTANT, UNIMPORTANT and CRITICAL")
    @PostMapping
    public ServiceTask createNewServiceTask(@RequestBody CreateNewTaskRequest createNewTaskRequest){
       return serviceTaskService.createNewServiceTask(createNewTaskRequest.factoryDeviceId, createNewTaskRequest.category, createNewTaskRequest.description);
    }
    @Operation(summary = "Returns a service task with the given id")
    @GetMapping("/{serviceTaskId}")
    public ServiceTask getServiceTaskById(@PathVariable Long serviceTaskId) {
        return serviceTaskService.getServiceTaskById(serviceTaskId).orElseThrow(() -> new ServiceTaskNotFoundException("Task not found with given id: ", serviceTaskId));

    }
    @Operation(summary = "Updates the state of the task to closed")
    @PatchMapping("/{serviceTaskId}")
    public String updateServiceTaskState(@PathVariable("serviceTaskId") Long serviceTaskId){
        return serviceTaskService.updateServiceTaskState(serviceTaskId);
    }

    @Operation(summary = "Returns a list of all of the service tasks related to a certain device id, ordered by category firstly and secondly by creation date")
    @GetMapping("/device/{factoryDeviceId}")
    public List<ServiceTask> getServiceTasksByFactoryDeviceId(@PathVariable("factoryDeviceId") Long factoryDeviceId){
        return serviceTaskService.getServiceTasksByFactoryDeviceId(factoryDeviceId);
    }
    @Operation(summary = "Deletes a service task by the given id")
    @DeleteMapping("/{serviceTaskId}")
    public String deleteServiceTaskById(@PathVariable("serviceTaskId") Long serviceTaskId ){
        return serviceTaskService.deleteServiceTaskById(serviceTaskId);
    }
    @Operation(summary = "Update a service task with new description and category, valid categories are IMPORTANT, UNIMPORTANT and CRITICAL")
    @PutMapping("/{serviceTaskId}")
    public ServiceTask updateServiceTaskById(@PathVariable("serviceTaskId") Long serviceTaskId, @RequestBody UpdateTaskRequest updateTaskRequest){
        return serviceTaskService.updateServiceTaskById(serviceTaskId, updateTaskRequest.description, updateTaskRequest.category);
    }

}
