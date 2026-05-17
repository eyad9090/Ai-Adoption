package com.example.employeemanagement.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<Employee> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public Employee getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee create(@Valid @RequestBody Employee employee) {
    return service.create(employee);
  }

  @PutMapping("/{id}")
  public Employee update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
    return service.update(id, employee);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
