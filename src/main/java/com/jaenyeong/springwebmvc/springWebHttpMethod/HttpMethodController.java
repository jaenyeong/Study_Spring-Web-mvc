package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
// 컨트롤러 모든 메서드가 Get 요청만 수신, 다른 요청시 에러
//@RequestMapping(method = RequestMethod.GET)
// 컨트롤러 수준에서도 설정 가능하지만 핸들러(메서드)에서 선언한 설정이 있는 경우 오버라이딩을 해서 중복 적용이 되지 않고 핸들러(메서드) 설정만 적용됨
//@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class HttpMethodController {

	// 핸들러 정의
//	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String hello() {
		return "hello";
	}

	@RequestMapping(value = {"/url", "urn"})
	@ResponseBody
	public String uriMapping() {
		return "url/uri";
	}

	// "/uri?", "/uri/?" 두 url은 다름
	@RequestMapping(value = {"/uri?", "/url/*", "/urn/**"})
	@ResponseBody
	public String uriPatternMapping() {
		return "uriPatternMapping";
	}

	// 글자만 허용
	// "/exp/1234"와 같은 경로는 찾지 못함 404 error
	@RequestMapping(value = "/exp/{name:[a-z]+}")
	@ResponseBody
	public String regularExpressionMapping(@PathVariable String name) {
		return name;
	}

	// 중복 URL 매핑 경우에 가장 구체적인 쪽으로 매핑됨
	// 스프링은 URI 확장자 매핑을 지원 "/jaenyeong.*" - 보안 이슈, 불확실성 등 사유로 사용 권장하지 않음
//	@RequestMapping(value = {"/**", "/jaenyeong.*"})
	// 다른 핸들러와 매핑 실패할 경우 항상 매핑되기 때문에 주의
//	@RequestMapping(value = "/**")
//	@ResponseBody
//	public String redundantPatternMapping() {
//		return "redundantPatternMapping";
//	}

	// content type : consumes
	// String을 반환해주는 것은 끝에 VALUE로 끝남 (APPLICATION_JSON_VALUE)
	// 서버에 content type을 JSON으로 설정한 경우 요청 헤더에 content type이 JSON으로 설정되어 있어야 함
	// 그렇지 않을 경우 415(unsupported Media) 에러 반환
	// accept : produces
	// 요청 헤더에 accept가 지정되어 있지 않는 경우에는 서버 핸들러에 accept가 지정되어 있더라도 매핑이 문제 없이 됨
// 	@RequestMapping(value = "/consumes", consumes = "application/json")
//	@RequestMapping(value = "/consumes", consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/consumes/produces", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String consumesPatternMapping() {
		return "consumesPatternMapping";
	}

	// 요청 헤더에 HttpHeaders.FROM로 설정한 경우
	@RequestMapping(value = "/headers", headers = HttpHeaders.FROM)               // 성공
//	@RequestMapping(value = "/headers", headers = HttpHeaders.ACCEPT)             // 성공
//	@RequestMapping(value = "/headers", headers = HttpHeaders.ACCEPT_LANGUAGE)    // 실패
//	@RequestMapping(value = "/headers", headers = "!" + HttpHeaders.FROM)         // 실패
//	@RequestMapping(value = "/headers", headers = HttpHeaders.FROM + "=" + "111") // 실패
	// "/headers?name=jaenyeong"과 같은 파라미터도 설정 가능
//	@RequestMapping(value = "/headers", params = "name=jaenyeong")
	@ResponseBody
	public String headersSetting() {
		return "headersSetting";
	}

	// param 설정시 요청이 HEAD 메서드라도 param이 있어야 함
	// OPTION 메서드는 상관 없음
//	@RequestMapping(value = "/headMethod", params = "name=jaenyeong")
	@RequestMapping(value = "/headMethod", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String headMethod() {
		return "headMethod";
	}

	// 커스텀 어노테이션 사용
	@GetHelloMapping
	@ResponseBody
	public String customAnnotation() {
		return "customAnnotation";
	}

	@GetMapping("/getEvents")
	@ResponseBody
	public String getEvents1() {
		return "getEvents";
	}

	@GetMapping("/getEvents/{id}")
	@ResponseBody
//	public String getEvents2(@PathVariable("id") int longId) {
	public String getEvents2(@PathVariable int id) {
		return "getEvents";
	}

	@PostMapping(
			value = "/createEvents",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String createEvents() {
		return "createEvents";
	}

	@DeleteMapping(
			value = "/deleteEvents/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String deleteEvents(@PathVariable int id) {
		return "deleteEvents";
	}

	@PutMapping(
			value = "/putEvents/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String putEvents(@PathVariable int id) {
		return "putEvents";
	}
}
