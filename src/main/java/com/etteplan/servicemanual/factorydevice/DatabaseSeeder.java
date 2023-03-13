package com.etteplan.servicemanual.factorydevice;


import com.opencsv.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final FactoryDeviceRepository factoryDeviceRepository;

    public DatabaseSeeder(FactoryDeviceRepository factoryDeviceRepository) {
        this.factoryDeviceRepository = factoryDeviceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create an instance of OpenCSV's CSVReader class to read the CSV file
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

        // Insert the devices into the database
        factoryDeviceRepository.saveAll(factoryDevices);
    }
}
