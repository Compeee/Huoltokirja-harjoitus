package com.etteplan.servicemanual.servicetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ServiceTask {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime taskRegisterDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskState state;
    @ManyToOne
    @JoinColumn(name = "factory_device_id")
    private FactoryDevice factoryDevice;

    @Enumerated(EnumType.STRING)
    private TaskCategory category;

    public ServiceTask() {
    }
    public Long getId() {
        return this.id;
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
        return state;
    }

    public void setTaskState(TaskState state) {
        this.state = state;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }
}
