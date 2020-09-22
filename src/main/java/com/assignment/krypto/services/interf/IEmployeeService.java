package com.assignment.krypto.services.interf;

import java.util.List;

import com.assignment.krypto.entity.Employee;

public interface IEmployeeService {

	Employee save(Employee emp);

	Employee update(Employee emp);

	void delete(Long employeeId);
	
	List<Employee> getAllEmployees(Long managerId);

	Employee getEmployee(Long employeeId);
}
