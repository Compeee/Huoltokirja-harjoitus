package com.etteplan.servicemanual.servicetask;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.OrderBy;
import java.util.List;

public interface ServiceTaskRepository extends JpaRepository<ServiceTask, Long>{

    // Orders the tasks by category first and secondly by the creation date
    @OrderBy("category ASC, creationDate DESC")
    List<ServiceTask> findAll();

    @OrderBy("category ASC, creationDate DESC")
    List<ServiceTask> findTasksByFactoryDeviceId(Long factoryDeviceId);

}
