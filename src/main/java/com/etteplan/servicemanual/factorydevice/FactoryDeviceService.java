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
    public List<FactoryDevice> getAllFactoryDevices(){
        return factoryDeviceRepository.findAll();
    }

    public Optional<FactoryDevice> getFactoryDeviceById(Long id){
        return factoryDeviceRepository.findById(id);
    }

}
