package com.jaenyeong.springwebmvc.servletAndSpring;

//@Configuration
//// 컨트롤러만 빈으로 등록
////@ComponentScan(useDefaultFilters = false, includeFilters = @ComponentScan.Filter(Controller.class))
//// 빈 전체 등록
//@ComponentScan
//// 어노테이션 기반 클래스 사용시 @EnableWebMvc 태깅
//// 스프링에 필요한 빈을 자동으로 등록해주는 기능
//@EnableWebMvc
//// DelegatingWebMvcConfiguration을 확장하는 지점을 WebMvcConfigurer 구현하여 설정
//public class WebConfig implements WebMvcConfigurer {
public class WebConfig {

	// handlerMapping 직접 빈 설정
//	@Bean
//	public HandlerMapping handlerMapping() {
//		RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
//		handlerMapping.setInterceptors();
//		handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE); // 가장 높은 우선 순위 지정
//		return handlerMapping;
//	}

	// handlerAdapter 직접 빈 설정
//	@Bean
//	public HandlerAdapter handlerAdapter() {
//		RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
//		// 메세지 컨버터 등을 설정할 수 있음
//		return handlerAdapter;
//	}

	// WebMvcConfigurer을 구현함으로 viewResolver를 직접 구현하지 않아도 됨
//	@Bean
//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setPrefix("/WEB-INF/");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;
//	}

//	// WebMvcConfigurer의 메서드를 오버라이딩하여 viewResolver를 빈 설정
//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.jsp("/WEB-INF/", ".jsp");
//	}
}
