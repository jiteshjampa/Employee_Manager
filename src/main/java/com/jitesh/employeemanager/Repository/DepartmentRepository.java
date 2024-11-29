package com.jitesh.employeemanager.Repository;

import com.jitesh.employeemanager.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
