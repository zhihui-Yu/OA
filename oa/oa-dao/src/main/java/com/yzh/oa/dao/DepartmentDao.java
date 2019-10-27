package com.yzh.oa.dao;

import java.util.List;

import com.yzh.oa.pojo.Department;

public interface DepartmentDao {
	void insert(Department dep);
	void update(Department dep);
	void delete(String sn);
	Department select(String sn);
	List<Department> selAll();
}
