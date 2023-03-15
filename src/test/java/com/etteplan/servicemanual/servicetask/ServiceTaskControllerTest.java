package com.etteplan.servicemanual.servicetask;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getServiceTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getServiceTaskByInvalidFactoryDeviceId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/tasks/device/99999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
