package com.etteplan.servicemanual.factorydevice;



import com.etteplan.servicemanual.exceptions.FactoryDeviceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;




import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class FactoryDeviceController {

    private final FactoryDeviceService factoryDeviceService;

    public FactoryDeviceController(FactoryDeviceService factoryDeviceService) {
        this.factoryDeviceService = factoryDeviceService;
    }

    @Operation(summary = "Returns a list of all of the factory devices")
    @GetMapping
    public List<FactoryDevice> getAllFactoryDevices() {
        return factoryDeviceService.getAllFactoryDevices();
    }

    @Operation(summary = "Returns a device with the given id")
    @GetMapping("/{factoryDeviceId}")
    public FactoryDevice getFactoryDeviceById(@PathVariable Long factoryDeviceId) {
        return factoryDeviceService.getFactoryDeviceById(factoryDeviceId)
                .orElseThrow(() -> new FactoryDeviceNotFoundException("Device not found with given id: ", factoryDeviceId));

    }
    @Operation(summary = "Creates a new factory device")
    @PostMapping
    public FactoryDevice createNewFactoryDevice(FactoryDevice factoryDevice){
        return factoryDeviceService.createNewFactoryDevice(factoryDevice);
    }
}