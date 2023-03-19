package com.etteplan.servicemanual.servicetask;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.OrderBy;
import java.util.List;

public interface ServiceTaskRepository extends JpaRepository<ServiceTask, Long>{

    // Finds all tasks and orders by category ascending and creation date descending
    List<ServiceTask> findAllByOrderByCategoryAscCreationDateDesc();

    // Finds all tasks related to a certain device and orders by category ascending and creation date descending
    List<ServiceTask> findTasksByFactoryDeviceIdOrderByCategoryAscCreationDateDesc(Long factoryDeviceId);

}
