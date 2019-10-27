package com.yzh.oa.service;

import java.util.List;

import com.yzh.oa.pojo.Employee;

public interface EmployeeService {
	void add(Employee employee);
	void edit(Employee employee);
	void remove(String sn);
	Employee get(String sn);
	List<Employee> getAll();
}
