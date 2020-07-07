package com.jaenyeong.springwebmvc.servletAndSpring;

// 스프링 3.1, 서블릿 3.0
// WebApplicationInitializer 구현
//public class WebApplication implements WebApplicationInitializer {
public class WebApplication {

//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		// demoWeb.xml 파일에 작성한 내용들과 동일한 내용
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//		// @EnableWebMvc 태깅하면 서블릿 컨텍스트를 참조하기 때문에
//		// DispatcherServlet가 사용하는 context에 servletContext를 반드시 설정 해줘야 함
//		context.setServletContext(servletContext);
//		context.register(WebConfig.class);
//		context.refresh();
//
//		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
//		ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
//		app.addMapping("/app/*");
//	}
}
