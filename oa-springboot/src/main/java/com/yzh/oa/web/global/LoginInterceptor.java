package com.yzh.oa.web.global;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器
 * 防止不登入而直接访问该系统的其他页面
 * @author listener
 *
 */
public class LoginInterceptor implements HandlerInterceptor{
	//放行前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取URl
		String url = request.getRequestURI();
		//判断是不是包含to_login 是则放行
		if(url.toLowerCase().contains("login")){
			return true;
		}
		//判断是不是登入过  是则放行
		HttpSession session = request.getSession();
		if(session.getAttribute("employee") != null){
			return true;
		}
		//其他情况都拦截 并返回登入界面
		response.sendRedirect("/to_login");
		return false;
	}
	//放行时
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}
	//放行后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
