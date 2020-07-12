package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 직접 명시적으로 사용하는 경우 Validator를 구현하지 않아도 됨
public class EventValidator implements Validator {
//public class EventValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Event event = (Event) target;
		if (event.getName().equalsIgnoreCase("aaa")) {
			errors.rejectValue("name", "wrongValue", "the value is not allowed");
		}
	}

	// Validator를 구현하지 않는 경우 아래처럼 메서드를 구현하여 사용 가능
//	public void validate(Event event, Errors errors) {
//		if (event.getName().equalsIgnoreCase("aaa")) {
//			errors.rejectValue("name", "wrongValue", "the value is not allowed");
//		}
//	}
}
