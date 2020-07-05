package com.jaenyeong.springwebmvc.mechanism.servletAndSpring;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Filter init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Filter doFilter");
		// 다음 필터로 전달
		// 마지막 필터일 때는 서블릿으로 연결됨
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("Filter destroy");
	}
}
