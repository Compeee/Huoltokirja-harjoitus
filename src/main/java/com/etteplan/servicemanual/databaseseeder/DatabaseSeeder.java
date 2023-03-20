package com.etteplan.servicemanual.databaseseeder;


import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import com.etteplan.servicemanual.servicetask.ServiceTask;
import com.etteplan.servicemanual.servicetask.ServiceTaskRepository;
import com.etteplan.servicemanual.servicetask.TaskCategory;
import com.etteplan.servicemanual.servicetask.TaskState;
import com.opencsv.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final FactoryDeviceRepository factoryDeviceRepository;
    private final ServiceTaskRepository serviceTaskRepository;

    public DatabaseSeeder(FactoryDeviceRepository factoryDeviceRepository, ServiceTaskRepository serviceTaskRepository) {
        this.factoryDeviceRepository = factoryDeviceRepository;
        this.serviceTaskRepository = serviceTaskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("seeddata.csv"));

        // Parse the CSV file into a list of factoryDevice objects
        List<FactoryDevice> factoryDevices = new ArrayList<>();
        String[] line;
        // Skip the first line, so it doesn't try adding the headers into the database
        boolean skipHeaders = true;
        while ((line = reader.readNext()) != null) {
            if(skipHeaders){
                skipHeaders = false;
                continue;
            }
            FactoryDevice factoryDevice = new FactoryDevice();
            factoryDevice.setName(line[0]);
            factoryDevice.setYear(Integer.parseInt(line[1]));
            factoryDevice.setType(line[2]);
            System.out.println(factoryDevice);
            factoryDevices.add(factoryDevice);
        }

        // Insert the tasks into the database

        factoryDeviceRepository.saveAll(factoryDevices);

       FactoryDevice factoryDevice = factoryDeviceRepository.findById(1L).orElseThrow(() -> new FactoryDeviceNotFoundException("Device not found with given id", 1L));
        List<ServiceTask> tasks = new ArrayList<ServiceTask>();
        ServiceTask task1 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.UNIMPORTANT);
        ServiceTask task2 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.CRITICAL);
        ServiceTask task3 = new ServiceTask(LocalDateTime.now(), "BROKEN", TaskState.OPEN, factoryDevice, TaskCategory.IMPORTANT);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        serviceTaskRepository.saveAll(tasks);


    }
}
