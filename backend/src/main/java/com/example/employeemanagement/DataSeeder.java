package com.example.employeemanagement;

import com.example.employeemanagement.employee.Employee;
import com.example.employeemanagement.employee.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds some demo data on startup (optional but helpful for beginners).
 *
 * If you don't want seed data, remove this file.
 */
@Configuration
public class DataSeeder {

  @Bean
  CommandLineRunner seed(EmployeeRepository repo) {
    return args -> {
      if (repo.count() > 0) return;

      repo.save(new Employee("John", "Doe", "john.doe@example.com", "IT", 65000.0));
      repo.save(new Employee("Jane", "Smith", "jane.smith@example.com", "HR", 52000.0));
      repo.save(new Employee("Ahmed", "Ali", "ahmed.ali@example.com", "Finance", 72000.0));
    };
  }
}
