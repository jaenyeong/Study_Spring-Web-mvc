package com.jaenyeong.springwebmvc.mechanism.servletAndSpring;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

	public String getName() {
		return "Hello Service";
	}
}
