package com.jitesh.employeemanager.Repository;

import com.jitesh.employeemanager.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
