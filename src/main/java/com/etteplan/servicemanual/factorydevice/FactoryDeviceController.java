package com.etteplan.servicemanual.factorydevice;



import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
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

    @GetMapping("/{factoryDeviceId}")
    public FactoryDevice getFactoryDeviceById(@PathVariable Long factoryDeviceId) {
        return factoryDeviceService.getFactoryDeviceById(factoryDeviceId)
                .orElseThrow(() -> new FactoryDeviceNotFoundException("Device not found with given id: ", factoryDeviceId));

    }
}