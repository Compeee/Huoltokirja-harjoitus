package com.etteplan.servicemanual.factorydevice;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FactoryDeviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FactoryDeviceService factoryDeviceService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void getFactoryDevices() throws Exception {
        FactoryDevice factoryDevice1 = new FactoryDevice(9999L,"TestDevice 1", 1999, "Type 5");
        FactoryDevice factoryDevice2 = new FactoryDevice(99999L,"TestDevice 2", 1999, "Type 5");
        List<FactoryDevice> devices = List.of(factoryDevice1, factoryDevice2);
        when(factoryDeviceService.getAllFactoryDevices()).thenReturn(devices);
        mvc.perform(MockMvcRequestBuilders.get("/api/devices").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getFactoryDeviceById() throws Exception {
        FactoryDevice factoryDevice = new FactoryDevice(9999L,"TestDevice 1", 1999, "Type 5");
        when(factoryDeviceService.getFactoryDeviceById(factoryDevice.getId())).thenReturn(Optional.of(factoryDevice));
        mvc.perform(MockMvcRequestBuilders.get("/api/devices/9999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(9999)))
                .andExpect(jsonPath("$.name", is("TestDevice 1")))
                .andExpect(jsonPath("$.year", is(1999)));
    }

    @Test
    public void getFactoryDeviceNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/devices/9939").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    @Test
    void createNewFactoryDevice() throws Exception {
        FactoryDevice factoryDevice = new FactoryDevice(99999L,"TestDevice 1", 1999, "Type 5");

        when(factoryDeviceService.createNewFactoryDevice(any(FactoryDevice.class)))
                .thenReturn(factoryDevice);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factoryDevice)))
                .andExpect(status().isOk());
    }
}