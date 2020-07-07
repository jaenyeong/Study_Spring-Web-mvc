package com.jaenyeong.springwebmvc.springWebConfigure.formatter;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest // @SpringBootApplication 태깅된 곳부터 모든 빈을 다 등록해줌
// @SpringBootTest 어노테이션을 사용하면 MockMvc가 자동으로 빈으로 등록되지 않아 @AutoConfigureMockMvc 사용하여 등록
@AutoConfigureMockMvc
public class SampleControllerTest {

//	@Autowired
//	MockMvc mockMvc;
//
//	@Autowired
//	PersonRepository personRepository;
//
//	// json 문자열 파싱을 위한 객체
//	@Autowired
//	ObjectMapper objectMapper;
//
//	// Jaxb2Marshaller 빈을 받는 마샬러
//	@Autowired
//	Marshaller marshaller;
//
////	@Test
////	public void hello() throws Exception {
////		this.mockMvc.perform(get("/hello/jaenyeong"))
////				.andDo(print()) // 콘솔 출력
////				.andExpect(content().string("hello jaenyeong"));
////	}
//
//	// WebConfig에서 포매터를 등록하지 않고 포매터 자체에 @Component 어노테이션 태깅만 한 경우 테스트 에러 발생
//	// @WebMvcTest 어노테이션은 슬라이드 테스트용. 웹과 관련된 빈만 등록하기 때문에 테스트 실패
//	// WebConfig 설정은 웹과 관련된 설정으로 인식
//	// 이를 해결하려면 테스트 내에서 명시적으로 빈을 등록 해주거나 또는 @SpringBootTest 사용하여 통합테스트로 변경
//	@Test
//	public void hello() throws Exception {
//		Person person = new Person();
//		person.setName("jaenyeong");
//		Person savedPerson = personRepository.save(person);
//
//		// @Entity 어노테이션 태깅만으로 컨버터를 따로 구현할 필요 없이 테스트가 성공해야 하지만
//		// MethodArgumentConversionNotSupportedException 예외 발생하며 테스트 실패
//		this.mockMvc.perform(
//				get("/hello")
////						.param("name", "jaenyeong"))
////						.param("id", "1"))
//						.param("id", savedPerson.getId().toString()))
//				.andDo(print()) // 콘솔 출력
//				.andExpect(content().string("hello jaenyeong"));
//	}
//
//	// 리소스 핸들러 테스트
//	@Test
//	public void helloStatic() throws Exception {
//		this.mockMvc.perform(
//				get("/index.html"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().string(Matchers.containsString("hello index")));
//	}
//
//	@Test
//	public void helloMobile() throws Exception {
//		this.mockMvc.perform(
//				get("/mobile/index.html"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().string(Matchers.containsStringIgnoringCase("hello mobile")))
//				.andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
//	}
//
//	@Test
//	public void stringMessage() throws Exception {
//		this.mockMvc.perform(
//				get("/message")
//						.content("message"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().string("message"));
//	}
//
//	@Test
//	public void jsonMessage() throws Exception {
//		Person person = new Person();
//		person.setId(2020L);
//		person.setName("jaenyeong");
//
//		// ObjectMapper를 이용해 json 문자열 생성
//		String jsonString = objectMapper.writeValueAsString(person);
//
//		this.mockMvc.perform(
//				get("/messageConverter")
//						// 컨버터 타입 지정시 컨텐츠 정보와 요청 헤더 정보를 참고
//						// 스프링 5.2 버전 이후부터는 UTF-8이 기본 캐릭터 셋. UTF-8은 deprecated
//						.contentType(MediaType.APPLICATION_JSON)
//						// 클라이언트가 원하는 타입
//						.accept(MediaType.APPLICATION_JSON)
//						.content(jsonString))
//				.andDo(print())
//				.andExpect(status().isOk())
//				// json 본문을 확인할 때 json path 사용
//				.andExpect(jsonPath("$.id").value(2020))
//				.andExpect(jsonPath("$.name").value("jaenyeong"));
//	}
//
//	@Test
//	public void xmlMessage() throws Exception {
//		Person person = new Person();
//		person.setId(2020L);
//		person.setName("jaenyeong");
//
//		// 마샬링
//		StringWriter stringWriter = new StringWriter();
//		Result result = new StreamResult(stringWriter);
//		marshaller.marshal(person, result);
//		String xmlString = stringWriter.toString();
//
//		this.mockMvc.perform(
//				get("/messageConverter")
//						// 컨버터 타입 지정시 컨텐츠 정보와 요청 헤더 정보를 참고
//						// 스프링 5.2 버전 이후부터는 UTF-8이 기본 캐릭터 셋. UTF-8은 deprecated
//						.contentType(MediaType.APPLICATION_XML)
//						// 클라이언트가 원하는 타입
//						.accept(MediaType.APPLICATION_XML)
//						.content(xmlString))
//				.andDo(print())
//				.andExpect(status().isOk())
//				// xml 본문을 확인할 때 xpath 사용
//				.andExpect(xpath("person/id").string("2020"))
//				.andExpect(xpath("person/name").string("jaenyeong"));
//	}
}
