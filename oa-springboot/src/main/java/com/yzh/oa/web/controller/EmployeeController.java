package com.yzh.oa.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import com.yzh.oa.global.Contant;
import com.yzh.oa.dao.pojo.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.yzh.oa.service.DepartmentService;
import com.yzh.oa.service.EmployeeService;
/**
 * 员工管理
 * @author listener
 *
 */
@Controller("employeeController")
@RequestMapping("/employee")
public class EmployeeController {
	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private DepartmentService departmentService;
	
	/**
	 * 查询员工信息 将查到的员工信息放入map中(相当于json格式)
	 * @param map
	 * @return
	 */
	@RequestMapping("list")
	public String list(Map<String, Object> map){
		map.put("list", employeeService.getAll());
		return "employee_list";
	}
	
	/**
	 * 添加员工时候 返回一个空员工对象给表单提交  和部门列表 职位列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAdd(Map<String,Object> map){
		map.put("addEmployee", new Employee());
		map.put("dlist", departmentService.getAll());
		map.put("plist", Contant.getPosts());
		return "employee_add";
	}
	
	@RequestMapping("/add")
	public String toAdd(Employee employee){
		employeeService.add(employee);
		return "redirect:list";
	}
	
	/**
	 * 更新员工信息 给表单一个空对象  返回部门列表 职位列表
	 * @param sn
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/to_update", params="sn")
	public String toUpdate(String sn, Map<String,Object> map){
		map.put("upEmployee", employeeService.get(sn));
		map.put("dlist", departmentService.getAll());
		map.put("plist", Contant.getPosts());
		return "employee_update";
	}
	
	@RequestMapping("/update")
	public String update(Employee employee){
		employeeService.edit(employee);
		return "redirect:list";
	}
	
	/**
	 * 删除员工
	 * @param sn
	 * @return
	 */
	@RequestMapping(value="/remove", params="sn")
	public String remove(String sn){
		employeeService.remove(sn);
		return "redirect:list";
	}
	
}
