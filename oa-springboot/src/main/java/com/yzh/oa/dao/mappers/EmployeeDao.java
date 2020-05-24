package com.yzh.oa.dao.mappers;

import java.util.List;

import com.yzh.oa.dao.pojo.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeDao {
	void insert(Employee employee);

	void update(Employee employee);

	void delete(String sn);

	Employee select(String sn);

	List<Employee> selAll();

	List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn, @Param("post") String post);
}
