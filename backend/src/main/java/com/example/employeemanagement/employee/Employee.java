package com.example.employeemanagement.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Employee JPA entity.
 *
 * Beginner note:
 * - @Entity tells JPA this class maps to a database table.
 * - @Id + @GeneratedValue means the database generates the primary key.
 */
@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First name is required")
  @Size(min = 2, max = 50, message = "First name must be 2-50 characters")
  @Column(nullable = false)
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Size(min = 2, max = 50, message = "Last name must be 2-50 characters")
  @Column(nullable = false)
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  @Size(max = 120, message = "Email must be <= 120 characters")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Department is required")
  @Size(min = 2, max = 80, message = "Department must be 2-80 characters")
  @Column(nullable = false)
  private String department;

  @NotNull(message = "Salary is required")
  @PositiveOrZero(message = "Salary must be >= 0")
  @Column(nullable = false)
  private Double salary;

  public Employee() {
    // JPA needs a default constructor
  }

  public Employee(String firstName, String lastName, String email, String department, Double salary) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.department = department;
    this.salary = salary;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }
}
