package com.yzh.oa.service.impl;

import com.yzh.oa.dao.mappers.DepartmentDao;
import com.yzh.oa.dao.pojo.Department;
import com.yzh.oa.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Resource
	private DepartmentDao departmentDao;
	
	@Override
	public void add(Department dep) {
		departmentDao.insert(dep);
	}

	@Override
	public void edit(Department dep) {
		departmentDao.update(dep);
	}

	@Override
	public void remove(String sn) {
		departmentDao.delete(sn);
	}

	@Override
	public Department get(String sn) {
		return departmentDao.select(sn);
	}

	@Override
	public List<Department> getAll() {
		return departmentDao.selAll();
	}

}
