package com.assignment.krypto.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.krypto.entity.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long>{

}
