package com.yzh.oa.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.yzh.oa.dao.pojo.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.yzh.oa.service.GlobalService;

/**
 * 登入控制器类
 * 
 * @author listener
 *
 */
@Controller
public class GlobalController {
	@Resource
	private GlobalService globalServiceImpl;

	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}

	/**
	 * 判断是否有这个员工 有则把员工对象放session中后跳转个人信息页面 无则返回登入界面
	 * 
	 * @param session
	 * @param sn
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpSession session, @RequestParam String sn, @RequestParam String password) {
		System.out.println("login");
		Employee employee = globalServiceImpl.login(sn, password);
		if (employee == null) {
			return "redirect:to_login";
		}
		session.setAttribute("employee", employee);
		return "redirect:self";
	}

	@RequestMapping("/self")
	public String self() {
		return "self";
	}

	/**
	 * 退出时候删除session中的员工对象
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/quit")
	public String quit(HttpSession session) {
		session.setAttribute("employee", null);
		return "redirect:to_login";
	}

	@RequestMapping("/to_change_password")
	public String toChangePassword() {
		return "change_password";
	}

	/**
	 * 修改密码 获取session对象 判断旧密码是否正确 正确则判断新密码两次是不是输入正确 正确返回个人信息页面 其他情况返回修改密码界面
	 * 
	 * @param session
	 * @param old
	 * @param new1
	 * @param new2
	 * @return
	 */
	@RequestMapping("/change_password")
	public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1,
			@RequestParam String new2) {
		Employee employee = (Employee) session.getAttribute("employee");
		if (employee.getPassword().equals(old)) {
			if (new1.equals(new2)) {
				return "redirect:self";
			}
		}
		return "redirect:to_change_password";
	}
}
