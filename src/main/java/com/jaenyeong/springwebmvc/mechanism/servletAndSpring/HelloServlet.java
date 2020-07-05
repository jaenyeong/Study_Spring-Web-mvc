package com.jaenyeong.springwebmvc.mechanism.servletAndSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// HttpServlet을 상속받아야 서블릿이 될 수 있음
// 서블릿은 자원을 공유하는 스레드를 생성하여 요청을 처리함
// CGI에 비해 : 빠름, 플랫폼 독립적-이식성, 컨테이너가 지원하는 보안 기능
// 톰캣, 제티 등은 서블릿 스펙을 준수하는 서블릿 컨테이너
// 컨테이너는 서블릿 라이프 사이클을 관리 - 세션관리, 네트워크 서비스, MIME 기반 인코딩과 디코딩
// 서블릿 생명주기 : init > doGet등 메서드 호출 > destory
public class HelloServlet extends HttpServlet {

	// 서블릿 생성 시점에 딱 한 번 호출
	@Override
	public void init() throws ServletException {
		System.out.println("HttpServlet init");
	}

	// 서블릿이 생성된 후에는 doGet만 호출
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("HttpServlet doGet");

		ApplicationContext context =
				(ApplicationContext) getServletContext()
						.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);;
		HelloService helloService = context.getBean(HelloService.class);

//		resp.getWriter().write("Hello Servlet");
		resp.getWriter().write("<html>");
		resp.getWriter().write("<head>");
		resp.getWriter().write("</head>");
		resp.getWriter().write("<body>");
		resp.getWriter().write("<h1>Hello Servlet!!!@</h1>");
		// 리스너에서 바인딩한 값 출력
//		resp.getWriter().write("<h2>listener Attr : " + getServletContext().getAttribute("name") + "</h2>");
//		resp.getWriter().write("<h2>Service Name : " + helloService.getName() + "</h2>");
		resp.getWriter().write("</body>");
		resp.getWriter().write("</html>");
	}

	// 서블릿 종료 시점에 딱 한 번 호출
	@Override
	public void destroy() {
		System.out.println("HttpServlet destroy");
	}
}
