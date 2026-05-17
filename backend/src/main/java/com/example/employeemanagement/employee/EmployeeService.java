package com.example.employeemanagement.employee;

import com.example.employeemanagement.exception.BadRequestException;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

  private final EmployeeRepository repo;

  public EmployeeService(EmployeeRepository repo) {
    this.repo = repo;
  }

  public List<Employee> getAll() {
    return repo.findAll();
  }

  public Employee getById(Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id=" + id));
  }

  public Employee create(Employee employee) {
    // Simple beginner-friendly rule: email must be unique
    if (repo.existsByEmail(employee.getEmail())) {
      throw new BadRequestException("Email is already in use: " + employee.getEmail());
    }

    employee.setId(null); // make sure it is treated as new
    return repo.save(employee);
  }

  public Employee update(Long id, Employee updated) {
    Employee existing = getById(id);

    // If user is changing email, ensure unique
    if (!existing.getEmail().equalsIgnoreCase(updated.getEmail())
        && repo.existsByEmail(updated.getEmail())) {
      throw new BadRequestException("Email is already in use: " + updated.getEmail());
    }

    existing.setFirstName(updated.getFirstName());
    existing.setLastName(updated.getLastName());
    existing.setEmail(updated.getEmail());
    existing.setDepartment(updated.getDepartment());
    existing.setSalary(updated.getSalary());

    return repo.save(existing);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) {
      throw new ResourceNotFoundException("Employee not found with id=" + id);
    }
    repo.deleteById(id);
  }
}
