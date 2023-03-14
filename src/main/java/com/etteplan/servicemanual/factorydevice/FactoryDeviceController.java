package com.etteplan.servicemanual.factorydevice;


import org.springframework.web.bind.annotation.*;




import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class FactoryDeviceController {

    private final FactoryDeviceService factoryDeviceService;

    public FactoryDeviceController(FactoryDeviceService factoryDeviceService) {
        this.factoryDeviceService = factoryDeviceService;
    }

    @GetMapping
    public List<FactoryDevice> getAllFactoryDevices() {
        return factoryDeviceService.getAllFactoryDevices();
    }

    @GetMapping("/{id}")
    public FactoryDevice getFactoryDeviceById(@PathVariable Long id) {
        return factoryDeviceService.getFactoryDeviceById(id).orElseThrow(() -> new FactoryDeviceNotFoundException(id));

    }
}