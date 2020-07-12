package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import static org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping$HttpOptionsHandler;

// 스프링 테스트 실행을 돕는 기능
// 내부적으로 applicationContext를 만들어줌
@RunWith(SpringRunner.class)
@WebMvcTest
public class HttpMethodControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void helloTest() throws Exception {
		// @RequestMapping 경우 get, post, put 등 http 메서드 다 받음
//		mockMvc.perform(post("/hello"))
		mockMvc.perform(get("/hello"))
				.andDo(print())
//				.andExpect(status().isMethodNotAllowed())
				.andExpect(status().isOk())
				.andExpect(content().string("hello"));

		mockMvc.perform(post("/hello"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("hello"));

		mockMvc.perform(put("/hello"))
				.andDo(print())
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void uriMappingTest() throws Exception {
		mockMvc.perform(post("/url"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("url/uri"));

		mockMvc.perform(put("/urn"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("url/uri"));
	}

	@Test
	public void uriPatternMappingTest() throws Exception {
		mockMvc.perform(post("/uri2"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("uriPatternMapping"));

		mockMvc.perform(post("/url/jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("uriPatternMapping"));

		mockMvc.perform(post("/urn/jaenyeong/book"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("uriPatternMapping"));
	}

	@Test
	public void regularExpressionMappingTest() throws Exception {
		// 문자만 허용
		mockMvc.perform(get("/exp/jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("jaenyeong"));

		// "/exp/1234"와 같은 경로는 찾지 못함 404 error
		// "/**" 경로 핸들러로 인해 주석처리
//		mockMvc.perform(get("/exp/1234"))
//				.andDo(print())
//				.andExpect(status().isNotFound());
	}

	@Test
	public void redundantPatternMappingTest() throws Exception {
		mockMvc.perform(post("/url"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("url/uri"))
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("uriMapping"));

		mockMvc.perform(post("/url/jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("uriPatternMapping"))
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("uriPatternMapping"));

		mockMvc.perform(get("/exp/jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("jaenyeong"))
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("regularExpressionMapping"));
	}

	@Test
	public void consumesPatternMappingTest() throws Exception {
		mockMvc.perform(get("/consumes/produces")
						// 둘 다 가능, UTF8도 가능
//						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("consumesPatternMapping"))
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("consumesPatternMapping"));
	}

	@Test
	public void headersSettingTest() throws Exception {
		mockMvc.perform(get("/headers")
				.header(HttpHeaders.FROM, "localhost"))
//				.header(HttpHeaders.AUTHORIZATION, "localhost"))
//				.header(HttpHeaders.FROM, "111"))
//				.param("name", "jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("headersSetting"))
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("headersSetting"));
	}

	@Test
	public void headMethodTest() throws Exception {
		// 헤드 요청시에는 서버에 param 설정 등 또한 매핑되어야 함
		mockMvc.perform(head("/headMethod"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("headMethod"));

		// OPTION 메서드 실행시
		mockMvc.perform(options("/headMethod"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().exists(HttpHeaders.ALLOW))
				// 실패
//				.andExpect(header().stringValues(HttpHeaders.ALLOW, "GET", "POST", "HEAD", "OPTIONS"))
				.andExpect(header().stringValues(
						HttpHeaders.ALLOW, hasItems(
//								containsStringIgnoringCase("GET"),
//								containsStringIgnoringCase("POST"),
//								containsStringIgnoringCase("HEAD"),
//								containsStringIgnoringCase("OPTIONS")
								containsString("GET"),
								containsString("POST"),
								containsString("HEAD"),
								containsString("OPTIONS")
						)))
		;
	}

	@Test
	public void customAnnotationTest() throws Exception {
		mockMvc.perform(get("/helloCustomMapping"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("customAnnotation"))
				.andExpect(handler().handlerType(HttpMethodController.class))
				.andExpect(handler().methodName("customAnnotation"));
	}

	@Test
	public void getEventsTest() throws Exception {
//		mockMvc.perform(get("/getEvents"))
//				.andExpect(status().isOk());

		// @pathVariable
		mockMvc.perform(get("/getEvents/1"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/getEvents/2"))
				.andExpect(status().isOk());
		mockMvc.perform(get("/getEvents/3"))
				.andExpect(status().isOk());
	}

	@Test
	public void createEventsTest() throws Exception {
		mockMvc.perform(
				post("/createEvents")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteEventsTest() throws Exception {
//		mockMvc.perform(get("/getEvents"))
//				.andExpect(status().isOk());

		// @pathVariable
		mockMvc.perform(
				delete("/deleteEvents/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		mockMvc.perform(
				delete("/deleteEvents/2")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		mockMvc.perform(
				delete("/deleteEvents/3")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void updateEventsTest() throws Exception {
		mockMvc.perform(
				put("/putEvents/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		mockMvc.perform(
				put("/putEvents/2")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		mockMvc.perform(
				put("/putEvents/3")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
