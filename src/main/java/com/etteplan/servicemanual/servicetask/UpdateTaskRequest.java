package com.etteplan.servicemanual.servicetask;

public class UpdateTaskRequest {
    public final String description;
    public final TaskCategory category;

    public UpdateTaskRequest(String description, TaskCategory category) {
        this.description = description;
        this.category = category;
    }
}
