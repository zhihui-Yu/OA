package com.yzh.oa.service;

import com.yzh.oa.pojo.Employee;
/**
 * 登入的业务层
 * @author listener
 *
 */
public interface GlobalService {
	//登入验证
	Employee login(String sn, String password);
	//修改密码
	void changePassword(Employee employee);
}
