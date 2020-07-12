package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HandlerMethodController_1 {

	@GetMapping("/argument")
	@ResponseBody // 어노테이션을 태깅하지 않으면 반환하는 문자열을 뷰리졸버가 뷰네임과 매핑시켜 뷰를 찾음
//	public String argument(WebRequest request) {
//	public String argument(NativeWebRequest request) {
//	public String argument(HttpServletRequest request, HttpServletResponse response) {
//	public String argument(InputStream requestBody, OutputStream responseBody) {       // java.io
//	public String argument(Reader requestBody, Writer responseBody) {                  // java.io
//	public String argument(PushBuilder pushBuilder) {
//	public String argument(HttpMethod httpMethod) {                                    // @RequestMapping 경우
//	public String argument(Locale locale, TimeZone timeZone, ZoneId zoneId) {
//	public View argument() {    // View를 직접 반환
//  public Model argument() {   // 모델 정보만 반환. RequestToViewNameTranslator가 요청 URI를 통해 뷰네임을 유추
    public String argument() {

		return "argument";
	}

//	public ResponseEntity<String> responseEntity() {
////		return ResponseEntity.ok().build();
////		return new ResponseEntity<>("success", HttpStatus.OK);
//	}
}
