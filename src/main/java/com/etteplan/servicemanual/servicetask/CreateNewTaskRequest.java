package com.etteplan.servicemanual.servicetask;

class CreateNewTaskRequest {
    public final Long factoryDeviceId;
    public final String description;
    public final TaskCategory category;

    public CreateNewTaskRequest(Long factoryDeviceId, String description, TaskCategory category) {
        this.factoryDeviceId = factoryDeviceId;
        this.description = description;
        this.category = category;
    }
}
