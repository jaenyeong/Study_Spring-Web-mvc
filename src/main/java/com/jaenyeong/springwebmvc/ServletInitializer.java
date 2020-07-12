package com.jaenyeong.springwebmvc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// War 패키징으로 프로젝트 생성시 추가되어 있음
// 독립적인 파일 형태로 배포시엔 SpringWebMvcApplication 클래스를 기반으로 패키징하고
// 서블릿컨테이너에 배포해 사용할 수 있는 형태로 패키징할 때 사용
public class ServletInitializer extends SpringBootServletInitializer {
//public class ServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringWebMvcApplication.class);
	}
}
