package com.yzh.oa.service;

import com.yzh.oa.dao.pojo.Department;

import java.util.List;

public interface DepartmentService {
	void add(Department dep);
	void edit(Department dep);
	void remove(String sn);
	Department get(String sn);
	List<Department> getAll();
}
