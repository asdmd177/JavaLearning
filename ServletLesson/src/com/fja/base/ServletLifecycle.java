package com.fja.base;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * Servlet 接口
 * -----|GenericServlet 抽象类
 * -------------|HttpServlet 基于Http协议的servlet 有doGet doPost方法
 * 
 * Servlet的生命周期：
 *    1. Servlet程序的生命周期是由服务器(tomcat)控制的
 *    2. 构造方法、init()、service()、destroy()构成了servlet的生命周期
 */
public class ServletLifecycle extends HttpServlet{
	
	//构造方法，创建servlet对象的时候调用，第一次访问一个servlet的时候就是创建servlet的时候。
	public ServletLifecycle(){
		System.out.println("servlet对象被创建");
	}
	
	//init方法，创建完servlet对象的时候调用（注意是创建完）
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("调用init初始化");
	}
	
	//service方法，每次访问servlet时都会调用
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		super.service(req, res);
		System.out.println("service方法被调用");
	}
	
	//销毁，停止服务器或者是重新部署了web应用的时候都会销毁所有的servlet，在销毁servlet的时候就会调用destroy方法
	@Override
	public void destroy() {
		super.destroy();
		System.out.println("servlet对象销毁了");
	}
}