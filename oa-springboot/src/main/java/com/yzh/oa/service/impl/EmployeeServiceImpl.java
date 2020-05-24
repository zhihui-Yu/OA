package com.yzh.oa.service.impl;

import com.yzh.oa.dao.mappers.EmployeeDao;
import com.yzh.oa.dao.pojo.Employee;
import com.yzh.oa.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Resource
	private EmployeeDao employeeDao;
	
	@Override
	public void add(Employee employee) {
		employee.setPassword("123");
		employeeDao.insert(employee);
	}

	@Override
	public void edit(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public void remove(String sn) {
		employeeDao.delete(sn);
	}

	@Override
	public Employee get(String sn) {
		return employeeDao.select(sn);
	}

	@Override
	public List<Employee> getAll() {
		return employeeDao.selAll();
	}

}
