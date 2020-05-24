package com.yzh.oa.dao.mappers;

import com.yzh.oa.dao.pojo.Department;

import java.util.List;


public interface DepartmentDao {
	void insert(Department dep);
	void update(Department dep);
	void delete(String sn);
	Department select(String sn);
	List<Department> selAll();
}
