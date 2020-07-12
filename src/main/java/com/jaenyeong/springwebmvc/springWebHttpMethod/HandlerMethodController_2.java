package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
// 모델 어트리뷰트에서 아래 설정하는 이름과 일치하는 값을 세션에 바인딩 해줌
// 태깅된 컨트롤러에서만 적용됨
@SessionAttributes("event")
public class HandlerMethodController_2 {

	// 아래 주석처리 부분들을 모두 BaseController로 이동
//	@ModelAttribute
//	public void categories(Model model) {
//		model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
//	}
//
//	@ModelAttribute("categoriesReturnList")
//	public List<String> categoriesReturnList(Model model) {
//		return List.of("study", "seminar", "hobby", "social");
//	}
//
////	@Autowired
////	EventValidator eventValidator;
//	// 아래처럼 메서드에서 원하는 시점에 명시적으로 처리할 수 있음
////	eventValidator.validate(event, bindingResult);
//
//	// 데이터 바인딩을 커스터마이징
//	@InitBinder
////	@InitBinder("event") // 이와 같이 문자열을 지정하면 모델 어트리뷰트 이름에 맞는 객체가 바인딩 될때만 적용됨
//	public void initEventBinder(WebDataBinder webDataBinder) {
//		// ID 값을 받지 않을 경우
//		webDataBinder.setDisallowedFields("id");
//		// Validator 인터페이스를 구현한 커스터마이징 클래스를 추가
//		webDataBinder.addValidators(new EventValidator());
//	}
//
//	// 가장 구체적인 익셉션이 매핑됨
//	// "/error" 로 요청시 해당 메서드를 통해 익셉션 페이지 리턴
////	@ExceptionHandler
////	public String eventErrorHandler(EventException exception, Model model) {
//	// 여러 익셉션 처리
//	@ExceptionHandler({EventException.class, RuntimeException.class})
//	// 여러 익셉션 처리 경우 모두 받을 수 있는 상위타입으로 매개변수 설정
//	public String eventErrorHandler(RuntimeException exception, Model model) {
//		model.addAttribute("message", "event error");
//		return "error";
//	}
//
//	@ExceptionHandler
//	public String runtimeErrorHandler(RuntimeException exception, Model model) {
//		model.addAttribute("message", "runtime error");
//		return "error";
//	}
//
//	// Rest API 경우 ResponseEntity를 이용해 에러 메세지 반환
//	@ExceptionHandler
//	public ResponseEntity<String> restErrorHandler() {
//		return ResponseEntity.badRequest().body("REST API error message");
//	}

	@GetMapping("/uriPattern1/{id}")
	@ResponseBody
//    public Event getEvent(@PathVariable Integer id) {
	// WebConfig에서 UrlPathHelper의 semicolon 제거 설정 수정 필요
	// URI가 "/uriPattern/1;name=jaenyeong" 인 경우 아래와 같이 설정
	public Event uriPattern1(@PathVariable Integer id, @MatrixVariable(name = "name", pathVar = "id") String name) {
//    public Event getEvent(@MatrixVariable MultiValueMap<String, String> matrixVars) {
//    public Event getEvent(@MatrixVariable(pathVar="id") MultiValueMap<String, String> matrixVars) {
//    public Event getEvent(@PathVariable Integer id, @MatrixVariable String name) {
		Event event = new Event();
		event.setId(id);
//		event.setName(id.toString());
		event.setName(name);
		return event;
	}

	@GetMapping("/uriPattern2/{id}")
	@ResponseBody
	// PathVariable의 이름과 URL의 이름과 일치하지 않으면 이름 설정 필요
	public Event uriPattern2(@PathVariable("id") Integer idValue) {
		Event event = new Event();
		event.setId(idValue);
//		event.setName(idValue.toString());
		return event;
	}

	// 요청 매개변수
	// queryString, form 안에 key-value 모두 요청 매개변수라고 취급
	// @RequestParam, @ModelAttribute 생략 가능
	// queryString, form data 둘다 처리 가능
	@PostMapping("/requestParam1")
	@ResponseBody
	public Event requestParam1(
			@RequestParam(value = "name", required = false, defaultValue = "default") String name) {
		Event event = new Event();
		event.setId(0);
		event.setName(name);
		return event;
	}

	@PostMapping("/requestParam2")
	@ResponseBody
	public Event requestParam2(@RequestParam Map<String, String> params) {
		Event event = new Event();
		event.setId(0);
		event.setName(params.get("name"));
		return event;
	}

	@PostMapping("/requestParam3")
	@ResponseBody
	public Event requestParam3(
			@RequestParam() String name,
			@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
		Event event = new Event();
		event.setId(0);
		event.setName(name);
		event.setLimit(limit);
		return event;
	}

	@GetMapping("/formSubmit/request")
	public String formSubmitRequest(Model model) {
		Event newEvent = new Event();
		newEvent.setLimit(50);
		model.addAttribute("event", newEvent);
		return "/events/form";
	}

	@PostMapping("/formSubmit/events")
	@ResponseBody
//	public Event formSubmit(@RequestParam() String name, @RequestParam() Integer limit) {
	public Event formSubmit(String name, Integer limit) {
		Event event = new Event();
		event.setName(name);
		event.setLimit(limit);
		return event;
	}

	@PostMapping("/modelAttribute")
//	@PostMapping("/modelAttribute/name/{name}")
	@ResponseBody
//	public Event modelAttribute(@ModelAttribute Event event) {
	// BindingResult bindingResult 에 예외처리가 담기고 요청은 처리 됨
//	public Event modelAttribute(Event event, BindingResult bindingResult) {
	// @Valid 사용시 값은 바인딩되지만 (바인딩 후 밸리데이션 처리) BindingResult에 에러로 포함됨
	// @Valid은 그룹지정이 안됨
//	public Event modelAttribute(@Valid Event event, BindingResult bindingResult) {
	// @Valid를 사용한것과 같이 검증처리는 됨
//	public Event modelAttribute(@Valid @ModelAttribute Event event, BindingResult bindingResult) {
	// 아래와 같은 경우 @NotBlank 어노테이션은 적용되지 않고 @Min 어노테이션만 적용됨
//	public Event modelAttribute(@Validated(Event.ValidateLimit.class) Event event, BindingResult bindingResult) {
//	public Event modelAttribute(@Validated(Event.ValidateName.class) Event event, BindingResult bindingResult) {
	public Event modelAttribute(@Validated Event event, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("-----------------------------------");
			bindingResult.getAllErrors().forEach(System.out::println);
			System.out.println("-----------------------------------");
		}
		return event;
	}

	@PostMapping("/formSubmit/error/handle")
	public String formSubmitErrorHandle(@Validated Event event, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/events/form";
		}

		// DB에 저장한다고 가정
		List<Event> eventList = new ArrayList<>();
		eventList.add(event);
//		model.addAttribute("eventList", eventList);
		// 아래처럼 키명 생략 가능
		model.addAttribute(eventList);
//		return "/events/list";
		return "redirect:/events/list";
	}

	@GetMapping("/events/list")
	public String redirectEventList(Model model) {
		// DB에서 저장된 데이터가 있다고 가정
		Event event = new Event();
		event.setName("redirect");
		event.setLimit(10);

		List<Event> eventList = new ArrayList<>();
		eventList.add(event);

		model.addAttribute(eventList);

		return "/events/list";
	}

	@GetMapping("/events/session")
	public String sessionEvent(Model model, HttpSession httpSession, SessionStatus sessionStatus) {
		Event event = new Event();
		event.setLimit(20);

		model.addAttribute("event", event);
		httpSession.setAttribute("event", event);

		// 사용 완료시 세션 데이터를 사용한 곳에서 비우도록 알려줌
		// 비우지 않으면 값이 남아 있음
//		sessionStatus.setComplete();

		return "/events/form";
	}

	@GetMapping("/events/session/form/name")
	public String getEventsSessionFormName(Model model) {
		model.addAttribute("event", new Event());

		return "/events/form-name";
	}

	@PostMapping("/events/session/form/name")
	// @ModelAttribute는 세션에 있는 데이터도 바인딩 받음
	public String postEventsSessionFormName(@Validated @ModelAttribute Event event, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/events/form-name";
		}

		return "redirect:/events/session/form/limit";
	}

	@GetMapping("/events/session/form/limit")
	public String getEventsSessionFormLimit(@ModelAttribute Event event, Model model) {
		model.addAttribute("event", event);

		return "/events/form-limit";
	}

	@PostMapping("/events/session/form/limit")
	public String postEventsSessionFormLimit(
			@Validated @ModelAttribute Event event,
			BindingResult bindingResult,
			SessionStatus sessionStatus,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "/events/form-limit";
		}

		sessionStatus.setComplete();

		// 리다이렉트 시 아래와 같이 프리미티브 타입 데이터는 쿼리스트링으로 연결
		// 스프링부트는 이 기능이 기본적으로 꺼져있음
		// 확인용 설정
//		model.addAttribute("name", event.getName());
//		model.addAttribute("limit", event.getLimit());

		// 위와 같이 모델 데이터를 모두 넘기지 않고 특정 데이터만 지정해서 넘기고 싶은 경우
		redirectAttributes.addAttribute("name", event.getName());
		redirectAttributes.addAttribute("limit", event.getLimit());

		// flashAttributes 사용
		// 세션에 들어감
		// 리다이렉트 한 곳에서 처리가 완료되면 해당 데이터는 세션에서 제거됨
		Event flashEvent = new Event();
		flashEvent.setName("flash event");
		flashEvent.setLimit(777);
		redirectAttributes.addFlashAttribute("flashEvent", flashEvent);

		return "redirect:/events/session/form/visitTime";
	}

	@GetMapping("/events/session/form/visitTime")
	// @SessionAttribute도 다른 어노테이션과 마찬가지로 이름 매핑
//	public String getEventListSessionAttribute(Model model, @SessionAttribute("visitTime") LocalDateTime visitTime) {
	public String getEventListSessionAttribute(
			Model model,
			@RequestParam String name,
			@RequestParam Integer limit,
			// 모델 어트리뷰트 사용시 주의! 세션 어트리뷰트에서 사용한 이름과 동일하면 안됨
			// 동일한 이름 사용시 세션에서 데이터를 먼저 찾아보려고 함
			// 하지만 해당 예제에서는 호출전 세션을 비우기 때문에 모델 어트리뷰트의 매핑할 데이터를 찾지 못하고 에러 발생
//			@ModelAttribute Event event, // 이와 같이 세션 어트리뷰트와 같은 이름을 사용하지 말 것 - 에러 발생
			// 따라서 모델 어트리뷰트로 값을 바인딩 받는 경우 아래처럼 세션에서 사용한 이름과 다른 새로운 이름을 사용할 것
			@ModelAttribute("newEvent") Event event,
			// 주의
			// 아래와 깉이 바인딩하는 것은 redirectAttributes.addFlashAttribute 설정으로 담은 데이터 값이 아닌
			// 쿼리스트링으로 넘겨오는 데이터 값이 sessionFlashEvent 객체에 바인딩되는 것
//			@ModelAttribute("flashEvent") Event sessionFlashEvent,
			@SessionAttribute LocalDateTime visitTime,
			HttpSession httpSession) {

		// RedirectAttributes의 attribute 출력
		Event modelFlashEvent = (Event) model.asMap().get("flashEvent");
		if (modelFlashEvent != null) {
			System.out.println("modelFlashEvent name : " + modelFlashEvent.getName());
			System.out.println("modelFlashEvent limit : " + modelFlashEvent.getLimit());
		}

		// RedirectAttributes의 attribute 출력
		// httpSession에는 담겨있지 않음
//		Event flashEvent = (Event) httpSession.getAttribute("flashEvent");
//		if (flashEvent != null) {
//			System.out.println("flashEvent name : " + flashEvent.getName());
//			System.out.println("flashEvent limit : " + flashEvent.getLimit());
//		}

		System.out.println("@SessionAttribute visitTime : " + visitTime);

		LocalDateTime visitTimeSession = (LocalDateTime) httpSession.getAttribute("visitTime");
		System.out.println("HttpSession visitTime : " + visitTimeSession);

		System.out.println("Request param name : " + name);
		System.out.println("Request param limit : " + limit);

		System.out.println("ModelAttribute name : " + event.getName());
		System.out.println("ModelAttribute limit : " + event.getLimit());

		// 객체 생성
		Event event1 = new Event();
		event1.setName("Session Attribute");
		event1.setLimit(10);

		Event event2 = new Event();
		event2.setName(name);
		event2.setLimit(limit);

		List<Event> eventList = new ArrayList<>();
		eventList.add(event1);
		eventList.add(event2);

		model.addAttribute(eventList);

		return "/events/list";
	}

	// 이런 경우 뷰네임은 RequestToViewNameTranslator 인터페이스가 요청과 일치하는 뷰네임을 리턴해줌
	// 현재는 해당 뷰를 처리할 페이지가 없기 때문에 주석처리
//	@GetMapping("/return/modelAttribute")
	// @ModelAttribute 어노테이션 생략 가능
//	@ModelAttribute
//	public Event modelAttributeCheck() {
//		return new Event();
//	}

	@GetMapping("/error")
	public String returnErrorPage() {
		throw new EventException();
	}
}
