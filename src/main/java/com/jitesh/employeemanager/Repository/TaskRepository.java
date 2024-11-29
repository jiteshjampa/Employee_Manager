package com.jitesh.employeemanager.Repository;

import com.jitesh.employeemanager.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
