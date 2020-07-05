package com.jaenyeong.springwebmvc.mechanism.servletAndSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// RequestMappingHandlerMapping이 찾아줌
// RequestMappingHandlerAdapter는 어노테이션 기반 핸들러 처리
//@RestController
@Controller
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	@ResponseBody
	// 아래와 같이 작성하여도 리턴타입이 아닌 메서드에 어노테이션을 태깅하는 것
	//	public @ResponseBody String hello() {
	public String hello() {
		System.out.println("Controller!");
		return "Hello Controller, " + helloService.getName();
	}

	@GetMapping("/sample")
	public String sample() {
		// 아래와 같이 경로 맨 앞에 '/'를 빼면 다른 요청으로 받아들여 서버 에러
//		return "WEB-INF/sample.jsp";

		// 설정 변경한 ViewResolver 빈 등록 전
//		return "/WEB-INF/sample.jsp";
		return "sample";
	}

	// view name 생략
//	@GetMapping("/sample")
//	public void sample() {
//	}
}
