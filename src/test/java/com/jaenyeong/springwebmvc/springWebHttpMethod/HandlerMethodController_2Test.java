package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HandlerMethodController_2Test {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void uriPattern1() throws Exception {

		mockMvc.perform(
//				get("/uriPattern1/1"))
				// @MatrixVariable 사용
				// WebConfigurer를 상속한 config에서 UrlPathHelper의 semicolon을 제거 설정 수정 필요
				get("/uriPattern1/1;name=jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1));
	}

	@Test
	public void uriPattern2() throws Exception {
		mockMvc.perform(
//				get("/uriPattern2/1"))
				get("/uriPattern2/1;name=jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1));
	}

	@Test
	public void requestParam1() throws Exception {
		mockMvc.perform(
				// queryString, form data 둘다 처리 가능
//				post("/requestParam1?name=jaenyeong"))
				post("/requestParam1").param("name", "jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"));

		// Map
		mockMvc.perform(
				post("/requestParam2")
						.param("name", "jaenyeong"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"));
	}

	@Test
	public void requestParam3() throws Exception {
		mockMvc.perform(
				post("/requestParam3")
						.param("name", "jaenyeong")
						.param("limit", "20"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("20"));
	}

	// 타임리프는 서블릿 컨테이너 엔진이 없어도 렌더링이 됨
	@Test
	public void formSubmitRequest() throws Exception {
		mockMvc.perform(
				get("/formSubmit/request"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("/events/form"))
				.andExpect(model().attributeExists("event"));
	}

	@Test
	public void formSubmit() throws Exception {
		mockMvc.perform(
				post("/formSubmit/events")
						.param("name", "jaenyeong")
						.param("limit", "50"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("50"));
	}

	@Test
	public void modelAttribute() throws Exception {
		mockMvc.perform(
				post("/modelAttribute")
						.param("name", "jaenyeong")
						.param("limit", "50"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("50"));

		mockMvc.perform(
				post("/modelAttribute?name=jaenyeong")
						.param("limit", "50"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("50"));

		// 경로가 "/modelAttribute/name/jaenyeong" 인 경우에도 가능
		// 아래처럼 테스트 가능
//		mockMvc.perform(
//				post("/modelAttribute/name/jaenyeong")
//						.param("limit", "50"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("name").value("jaenyeong"))
//				.andExpect(jsonPath("limit").value("50"));
	}

	@Test
	public void modelAttributeValidation() throws Exception {
		mockMvc.perform(
				post("/modelAttribute")
						.param("name", "jaenyeong")
						.param("limit", "-10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("-10"));
	}

	@Test
	public void formSubmitErrorHandle() throws Exception {
		ResultActions result = mockMvc.perform(
				post("/formSubmit/error/handle")
						.param("name", "jaenyeong")
						.param("limit", "-10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().hasErrors());

		ModelAndView mav = result.andReturn().getModelAndView();
		assert mav != null;
		Map<String, Object> model = mav.getModel();
		System.out.println(model.size());
	}

	@Test
	public void sessionEvent() throws Exception {
		MockHttpServletRequest request = mockMvc.perform(
				get("/events/session"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("/events/form"))
				.andExpect(model().attributeExists("event"))
				.andExpect(request().sessionAttribute("event", notNullValue()))
				.andReturn().getRequest();

		Object event = Objects.requireNonNull(request.getSession()).getAttribute("event");
		System.out.println(event);
	}

	@Test
	public void getEventListSessionAttribute() throws Exception {
		Event flashEvent = new Event();
		flashEvent.setName("flashEvent");
		flashEvent.setLimit(777);

		mockMvc.perform(
				get("/events/session/form/visitTime?name=abcd&limit=1234")
						.sessionAttr("visitTime", LocalDateTime.now())
						.flashAttr("flashEvent", flashEvent))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("newEvent"))
				.andExpect(model().attributeExists("flashEvent"))
				// HTML 파일에 <meta charset="UTF-8"> 태그로 인해 아래 테스트가 에러 발생
				// meta태그를 꼭 닫아줄 것 <meta charset="UTF-8"/>
				.andExpect(xpath("//p").nodeCount(2))
		;
	}

	@Test
	public void modelAttributeCheck() throws Exception {
		mockMvc.perform(
				get("/events/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("categories"))
				.andExpect(model().attributeExists("categoriesReturnList"));
	}
}
