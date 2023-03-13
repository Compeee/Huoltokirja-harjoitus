package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ServiceTask {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private LocalDateTime taskRegisterDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskState taskState;
    @OneToOne
    @JoinColumn(name = "factory_device_id")
    private FactoryDevice factoryDevice;

    @Enumerated(EnumType.STRING)
    private Category category;

    public ServiceTask() {
    }

    public FactoryDevice getFactoryDevice() {
        return factoryDevice;
    }

    public void setFactoryDevice(FactoryDevice factoryDevice) {
        this.factoryDevice = factoryDevice;
    }

    public LocalDateTime getTaskRegisterDate() {
        return taskRegisterDate;
    }

    public void setTaskRegisterDate(LocalDateTime taskRegisterDate) {
        this.taskRegisterDate = taskRegisterDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
