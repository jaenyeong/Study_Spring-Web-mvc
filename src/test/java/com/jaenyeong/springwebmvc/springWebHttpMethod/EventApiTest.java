package com.jaenyeong.springwebmvc.springWebHttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaenyeong.springwebmvc.springWebHttpMethod.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventApiTest {

	// JSON, 객체간 변환을 도와주는 객체
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void createEvent() throws Exception {
		Event event = new Event();
		event.setName("jaenyeong");
		// BindingResult 객체를 파라미터로 넣으면 에러가 BindingResult 객체에 바인딩 되고 400 에러를 돌려주지 않음
		event.setLimit(-20);

		String jsonValue = objectMapper.writeValueAsString(event);

		mockMvc.perform(
				post("/api/events/default")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonValue))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("-20"));
	}

	@Test
	public void testCreateEvent() throws Exception {
		Event event = new Event();
		event.setName("jaenyeong");
		event.setLimit(20);

		String jsonValue = objectMapper.writeValueAsString(event);

		mockMvc.perform(
				post("/api/events/httpEntity")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonValue))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("20"));
	}

	@Test
	public void responseBodyEvent() throws Exception {
		Event event = new Event();
		event.setName("jaenyeong");
		event.setLimit(20);

		String jsonValue = objectMapper.writeValueAsString(event);

		mockMvc.perform(
				post("/api/events/responseBody")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonValue)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("jaenyeong"))
				.andExpect(jsonPath("limit").value("20"));
	}

	@Test
	public void responseEntityEvent() throws Exception {
		Event event = new Event();
		event.setName("jaenyeong");
		event.setLimit(-20);

		String jsonValue = objectMapper.writeValueAsString(event);

		mockMvc.perform(
				post("/api/events/responseEntity")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonValue))
				.andDo(print())
				.andExpect(status().isBadRequest())
		;
	}
}
