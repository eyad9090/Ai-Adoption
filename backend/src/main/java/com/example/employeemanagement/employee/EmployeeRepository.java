package com.example.employeemanagement.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository.
 *
 * This automatically gives us common DB operations:
 * - save, findById, findAll, deleteById, etc.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  boolean existsByEmail(String email);
}
