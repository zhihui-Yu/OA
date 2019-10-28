package com.yzh.oa.service;

import java.util.List;

import com.yzh.oa.pojo.Department;

public interface DepartmentService {
	void add(Department dep);
	void edit(Department dep);
	void remove(String sn);
	Department get(String sn);
	List<Department> getAll();
}
