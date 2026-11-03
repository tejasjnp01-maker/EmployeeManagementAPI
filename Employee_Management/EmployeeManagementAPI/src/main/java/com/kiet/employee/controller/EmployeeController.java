package com.kiet.employee.controller;

import com.kiet.employee.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private final List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(1, "Amit Sharma", "IT", 60000));
        employees.add(new Employee(2, "Priya Singh", "HR", 52000));
        employees.add(new Employee(3, "Rahul Verma", "Finance", 58000));
    }

    // GET: All employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employees;
    }

    // GET: Employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return ResponseEntity.ok(employee);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // POST: Add employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    // PUT: Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee updatedEmployee) {

        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(updatedEmployee.getName());
                employee.setDepartment(updatedEmployee.getDepartment());
                employee.setSalary(updatedEmployee.getSalary());

                return ResponseEntity.ok(employee);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE: Remove employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employees.remove(employee);
                return ResponseEntity.ok("Employee deleted successfully");
            }
        }

        return ResponseEntity.notFound().build();
    }


}
