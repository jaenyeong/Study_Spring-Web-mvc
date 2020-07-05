package com.jaenyeong.springwebmvc.mechanism.servletAndSpring;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

// 스프링 3.1, 서블릿 3.0
// implements WebApplicationInitializer
public class WebApplication implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// demoWeb.xml 파일에 작성한 내용들과 동일한 내용
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);
		context.refresh();

		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
		app.addMapping("/app/*");
	}
}
