package com.yzh.oa.service.impl;


import com.yzh.oa.dao.mappers.EmployeeDao;
import com.yzh.oa.dao.pojo.Employee;
import com.yzh.oa.service.GlobalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登入界面业务层
 * @author listener
 *
 */
@Service("GlobalService")
public class GlobalServiceImpl implements GlobalService {
	
	@Resource
	private EmployeeDao employeeDao;
	
	@Override
	public Employee login(String sn, String password) {
		Employee employee = employeeDao.select(sn);
		if(employee!=null&&employee.getPassword().equals(password)){
			return employee;
		}else{
			return null;
		}
	}

	@Override
	public void changePassword(Employee employee) {
		employeeDao.update(employee);
	}

}
