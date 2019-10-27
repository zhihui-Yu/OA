package com.yzh.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzh.oa.pojo.Employee;

public interface EmployeeDao {
	void insert(Employee employee);

	void update(Employee employee);

	void delete(String sn);

	Employee select(String sn);

	List<Employee> selAll();

	List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn, @Param("post") String post);
}
