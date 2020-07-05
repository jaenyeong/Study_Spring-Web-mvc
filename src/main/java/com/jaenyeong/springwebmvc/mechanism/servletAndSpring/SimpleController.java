package com.jaenyeong.springwebmvc.mechanism.servletAndSpring;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// BeanNameUrlHandlerMapping이 찾아줌
// SimpleControllerHandlerAdapter
@org.springframework.stereotype.Controller("/simple")
public class SimpleController implements Controller {

	@Override
	public ModelAndView handleRequest(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 설정 변경한 ViewResolver 빈 등록 전
//		return new ModelAndView("/WEB-INF/simple.jsp");
		return new ModelAndView("simple");
	}

	// view name 생략
//	@Override
//	public ModelAndView handleRequest(
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		return new ModelAndView();
//	}
}
