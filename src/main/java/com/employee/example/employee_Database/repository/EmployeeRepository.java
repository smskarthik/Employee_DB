package com.employee.example.employee_Database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.example.employee_Database.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
