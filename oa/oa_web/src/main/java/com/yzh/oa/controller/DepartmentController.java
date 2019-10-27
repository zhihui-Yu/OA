package com.yzh.oa.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yzh.oa.pojo.Department;
import com.yzh.oa.service.DepartmentService;

/**
 * 部门管理
 * @author listener
 *
 */
@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {
	@Resource
	private DepartmentService departmentService;
	
	/**
	 * 将部门列表返回前端  跳转部门列表显示
	 * @param map
	 * @return
	 */
	@RequestMapping("list")
	public String list(Map<String, Object> map){
		map.put("list", departmentService.getAll());
		return "department_list";
	}
	
	/**
	 * 创建一个新的部门给前端表单使用 跳转添加部门页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAdd(Map<String,Object> map){
		map.put("department", new Department());
		return "department_add";
	}
	
	/**
	 * 数据库中添加部门 返回部门列表显示
	 * @param dep
	 * @return
	 */
	@RequestMapping("/add")
	public String toAdd(Department dep){
		departmentService.add(dep);
		return "redirect:list";
	}
	
	/**
	 * 获取需要修改的部门信息  传到前端 跳转修改部门页面
	 * @param sn
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/to_update", params="sn")
	public String toUpdate(String sn, Map<String,Object> map){
		map.put("department", departmentService.get(sn));
		return "department_update";
	}
	
	/** 
	 * 在数据库中修改部门 返回部门列表显示
	 * @param dep
	 * @return
	 */
	@RequestMapping("/update")
	public String update(Department dep){
		departmentService.edit(dep);
		return "redirect:list";
	}
	/**
	 * 删除部门 返回部门列表显示
	 * @param sn
	 * @return
	 */
	@RequestMapping(value="/remove", params="sn")
	public String remove(String sn){
		departmentService.remove(sn);
		return "redirect:list";
	}
	
}
