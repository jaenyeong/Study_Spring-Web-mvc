package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventApi {

	@PostMapping("/default")
	// HttpMessageConverter가 Event 객체로 컨버전함
	// 메서드 아규먼트를 리졸빙할 때 핸들러 어댑터들이 등록되어 있는 컨버터들을 사용(그 중에 컨버전을 할 수 있는 컨버터에게 위임)
	// 스프링 부트에서는 기본적으로 메세지 컨버터에 jackson이 자동 등록되어 있음
//	public Event createEvent(@RequestBody Event event) {
	// BindingResult 파라미터를 붙여주지 않으면 바인딩 에러 발생시 400 (Bad request)로 나가게 됨
	// BindingResult를 붙여주면 바인딩 에러가 발생해도 400 에러를 반환하지 않음
	// BindingResult 객체에 에러 정보를 바인딩
	public Event createEvent(@Validated @RequestBody Event event, BindingResult bindingResult) {
		// save event
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(System.out::println);
		}

		return event;
	}

	@PostMapping("/httpEntity")
	// HttpEntity 객체 사용시엔 @RequestBody 어노테이션 생략 가능
	// 대신 제네릭에 body 타입 지정 필요
	public Event createEvent(HttpEntity<Event> request) {
		// save event
		MediaType contentType = request.getHeaders().getContentType();
		System.out.println(contentType);

		return request.getBody();
	}

	@PostMapping("/responseBody")
	// @ResponseBody 어노테이션 태깅시 메서드 반환 데이터 값을 응답 본문에 삽입하여 반환
	// HttpMessageConverter를 사용하여 변환
	// 요청에 accept 헤더 참조
	// @RestController 어노테이션을 클래스에 태깅시 클래스에 모든 메서드가 @ResponseBody 태깅 효과
	// @RestController = @Controller + @ResponseBody
//	@ResponseBody
	public Event responseBodyEvent(@Validated @RequestBody Event event, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(System.out::println);
		}

		return event;
	}

	// @ResponseBody 대신 ResponseEntity 객체를 사용해 반환 가능
	// ResponseEntity 객체를 사용하면 @RestController 대신 @Controller를 사용 가능
	@PostMapping("/responseEntity")
	public ResponseEntity<Event> responseEntityEvent(@Validated @RequestBody Event event, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(System.out::println);

			return ResponseEntity.badRequest().build();
		}

		// 헤더, 상태정보, 바디 데이터 바인딩 가능
//		ResponseEntity responseEntity = new ResponseEntity();

		// 같은 처리
//		return ResponseEntity.ok(event);
		return ResponseEntity.ok().body(event);

		// 아래처럼 상태코드 변경하고 반환도 가능하지만 추천하지 않음
//		return new ResponseEntity<Event>(event, HttpStatus.CREATED);
	}
}
