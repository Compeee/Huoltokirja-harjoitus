package com.etteplan.servicemanual.factorydevice;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryDeviceService {
    private final FactoryDeviceRepository factoryDeviceRepository;
    public FactoryDeviceService(FactoryDeviceRepository factoryDeviceRepository) {
        this.factoryDeviceRepository = factoryDeviceRepository;
    }
    // Returns a list of all of the devices in the database
    public List<FactoryDevice> getAllFactoryDevices(){
        return factoryDeviceRepository.findAll();
    }

    // Returns a factory device by id
    public Optional<FactoryDevice> getFactoryDeviceById(Long id){
        return factoryDeviceRepository.findById(id);
    }

    // Creates a new device and saves it to the db
    public FactoryDevice createNewFactoryDevice(FactoryDevice factoryDevice) {
        return factoryDeviceRepository.save(factoryDevice);
    }
}
