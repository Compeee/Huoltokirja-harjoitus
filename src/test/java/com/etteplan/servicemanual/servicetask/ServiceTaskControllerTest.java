package com.etteplan.servicemanual.servicetask;


import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceTaskService serviceTaskService;

    @Autowired
    private ObjectMapper objectMapper;
    FactoryDevice factoryDevice = new FactoryDevice(9999L,"Device 1", 1999, "Type 5");
    @Test
    public void getServiceTasks() throws Exception {
        List<ServiceTask> tasks = Arrays.asList(
                new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT),
                new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.IMPORTANT),
                new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL)
        );
        when(serviceTaskService.getAllServiceTasks()).thenReturn(tasks);
        mvc.perform(MockMvcRequestBuilders.get("/api/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    public void getServiceTaskById() throws Exception {
        ServiceTask task = new ServiceTask(9999L, LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT);

        when(serviceTaskService.getServiceTaskById(task.getId())).thenReturn(Optional.of(task));

        mvc.perform(MockMvcRequestBuilders.get("/api/tasks/9999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(9999)))
                .andExpect(jsonPath("$.category", is("UNIMPORTANT")));
    }

    @Test
    public void getServiceTaskByInvalidId() throws Exception {
        ServiceTask task = new ServiceTask(9999L, LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT);

        when(serviceTaskService.getServiceTaskById(task.getId())).thenReturn(Optional.of(task));

        mvc.perform(MockMvcRequestBuilders.get("/api/tasks/2918249").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void createNewServiceTask() throws Exception {
        CreateNewTaskRequest request = new CreateNewTaskRequest(1L, "description", TaskCategory.UNIMPORTANT);

        ServiceTask task = new ServiceTask();
        task.setId(1L);
        task.setFactoryDevice(new FactoryDevice());
        task.setCategory(TaskCategory.UNIMPORTANT);
        task.setDescription("description");
        task.setTaskState(TaskState.OPEN);
        task.setCreationDate(LocalDateTime.now());

        // Mock the create new service task call
        when(serviceTaskService.createNewServiceTask(any(Long.class), any(TaskCategory.class), any(String.class)))
                .thenReturn(task);

        // Send a post request to create a new task
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    @Test
    void deleteServiceTaskById() throws Exception {
        // Create a new task
        ServiceTask task = new ServiceTask();
        task.setId(1L);
        task.setFactoryDevice(new FactoryDevice());
        task.setCategory(TaskCategory.UNIMPORTANT);
        task.setDescription("description");
        task.setTaskState(TaskState.OPEN);
        task.setCreationDate(LocalDateTime.now());

        // Mock the delete task by id service call
        when(serviceTaskService.deleteServiceTaskById(any(Long.class)))
                .thenReturn("Succesfully deleted task with id id: " + task.getId());
        // Send a delete request to delete the task
        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void updateTaskStateById() throws Exception {
        // Create a new task
        ServiceTask task = new ServiceTask();
        task.setId(1L);
        task.setFactoryDevice(new FactoryDevice());
        task.setCategory(TaskCategory.UNIMPORTANT);
        task.setDescription("description");
        task.setTaskState(TaskState.OPEN);
        task.setCreationDate(LocalDateTime.now());

        // Mock the service call to update the task state
        when(serviceTaskService.updateServiceTaskState(any(Long.class)))
                .thenReturn("Succesfully updated state of task id: " + task.getId());

        // Send a patch request to update the task state
        mvc.perform(MockMvcRequestBuilders
                        .patch("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void updateTaskInformation() throws Exception {
        UpdateTaskRequest request = new UpdateTaskRequest("updated", TaskCategory.CRITICAL);
        // Create a new task
        ServiceTask task = new ServiceTask();
        task.setId(1L);
        task.setFactoryDevice(new FactoryDevice());
        task.setCategory(TaskCategory.UNIMPORTANT);
        task.setDescription("description");
        task.setTaskState(TaskState.OPEN);
        task.setCreationDate(LocalDateTime.now());

        // Mock the service call to update the task state
        when(serviceTaskService.updateServiceTaskById(any(Long.class), any(UpdateTaskRequest.class)))
                .thenReturn(task);

        // Send a PUT request to update the task desc and category
        mvc.perform(MockMvcRequestBuilders
                        .put("/api/tasks/{id}", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


