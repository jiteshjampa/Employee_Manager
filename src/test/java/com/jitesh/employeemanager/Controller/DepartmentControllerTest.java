package com.jitesh.employeemanager.Controller;

import com.jitesh.employeemanager.Entity.Department;
import com.jitesh.employeemanager.Service.DepartmentService;
import com.jitesh.employeemanager.Controller.DepartmentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // This will initialize the mocks and inject dependencies
public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService; // Mock the service

    @InjectMocks
    private DepartmentController departmentController; // Inject mock service into the controller

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        // Prepare test data
        Department dept1 = new Department();
        dept1.setId(1L);
        dept1.setName("HR");

        Department dept2 = new Department();
        dept2.setId(2L);
        dept2.setName("IT");

        // Mock the service call to return the list of departments
        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(dept1, dept2));

        // Perform GET request and verify response
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].name").value("IT"));

        // Verify if the service method was called
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        // Prepare test data
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("HR");

        // Mock the service call to return the department when the id is 1
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(dept));

        // Perform GET request for department with id 1
        mockMvc.perform(get("/api/departments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HR"));

        // Verify if the service method was called with the correct ID
        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    public void testCreateDepartment() throws Exception {
        // Prepare test data
        Department dept = new Department();
        dept.setName("Sales");

        // Mock the service call to save the department
        when(departmentService.saveDepartment(dept)).thenReturn(dept);

        // Perform POST request to create a department
        mockMvc.perform(post("/api/departments")
                        .contentType("application/json")
                        .content("{\"name\": \"Sales\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sales"));

        // Verify if the service method was called
        verify(departmentService, times(1)).saveDepartment(dept);
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        // Prepare test data
        Department existingDept = new Department();
        existingDept.setId(1L);
        existingDept.setName("HR");

        Department updatedDept = new Department();
        updatedDept.setId(1L);
        updatedDept.setName("Finance");

        // Mock the service call
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(existingDept));
        when(departmentService.saveDepartment(updatedDept)).thenReturn(updatedDept);

        // Perform PUT request to update the department
        mockMvc.perform(put("/api/departments/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"name\": \"Finance\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Finance"));

        // Verify if the service method was called
        verify(departmentService, times(1)).saveDepartment(updatedDept);
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        // Prepare test data
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("HR");

        // Mock the service call
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(dept));

        // Perform DELETE request
        mockMvc.perform(delete("/api/departments/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verify if the service method was called
        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}
