package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

// 전역 컨트롤러
// 여기에 선언하면 모든 컨트롤러에 적용됨
// assignableTypes 속성으로 특정 컨트롤러에만 적용
@ControllerAdvice(assignableTypes = {HandlerMethodController_1.class, HandlerMethodController_2.class, EventApi.class})
public class BaseController {

	@ModelAttribute
	public void categories(Model model) {
		model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
	}

	@ModelAttribute("categoriesReturnList")
	public List<String> categoriesReturnList(Model model) {
		return List.of("study", "seminar", "hobby", "social");
	}

//	@Autowired
//	EventValidator eventValidator;
	// 아래처럼 메서드에서 원하는 시점에 명시적으로 처리할 수 있음
//	eventValidator.validate(event, bindingResult);

	// 데이터 바인딩을 커스터마이징
	@InitBinder
//	@InitBinder("event") // 이와 같이 문자열을 지정하면 모델 어트리뷰트 이름에 맞는 객체가 바인딩 될때만 적용됨
	public void initEventBinder(WebDataBinder webDataBinder) {
		// ID 값을 받지 않을 경우
		webDataBinder.setDisallowedFields("id");
		// Validator 인터페이스를 구현한 커스터마이징 클래스를 추가
		webDataBinder.addValidators(new EventValidator());
	}

	// 가장 구체적인 익셉션이 매핑됨
	// "/error" 로 요청시 해당 메서드를 통해 익셉션 페이지 리턴
//	@ExceptionHandler
//	public String eventErrorHandler(EventException exception, Model model) {
	// 여러 익셉션 처리
	@ExceptionHandler({EventException.class, RuntimeException.class})
	// 여러 익셉션 처리 경우 모두 받을 수 있는 상위타입으로 매개변수 설정
	public String eventErrorHandler(RuntimeException exception, Model model) {
		model.addAttribute("message", "event error");
		return "error";
	}

	@ExceptionHandler
	public String runtimeErrorHandler(RuntimeException exception, Model model) {
		model.addAttribute("message", "runtime error");
		return "error";
	}

	// Rest API 경우 ResponseEntity를 이용해 에러 메세지 반환
	@ExceptionHandler
	public ResponseEntity<String> restErrorHandler() {
		return ResponseEntity.badRequest().body("REST API error message");
	}
}
