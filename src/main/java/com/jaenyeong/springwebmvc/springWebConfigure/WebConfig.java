package com.jaenyeong.springwebmvc.springWebConfigure;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
public class WebConfig {
//public class WebConfig {
	// 스프링 부트 사용시 포매터를 빈으로 등록하면 아래와 같이 직접 등록할 필요가 없음
//	@Override
//	public void addFormatters(FormatterRegistry registry) {
//		registry.addFormatter(new PersonFormatter());
//	}

	// 인터셉터 추가
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// order는 작은 순서대로
//		registry.addInterceptor(new GreetingInterceptor()).order(0);
//		// addPathPatterns으로 특정 패턴만 지정
//		registry.addInterceptor(new AnotherInterceptor()).addPathPatterns("/hi").order(-1);
//	}

	// 리소스 핸들러 추가
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/mobile/**")
////				.addResourceLocations("classpath:/mobile/", "file:/Users/jaenyeongkim/files/")
//				.addResourceLocations("classpath:/mobile/")
//				// 스프링 4.1 이후부터 리소스 체인 - 캐시 사용 여부, 운영중일 때 true
////				.resourceChain(true)
//				// ResourceResolver, ResourceTransformer 추가도 가능
//				// 응답 헤더에 캐시 정보 추가
//				.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
//	}

	// 메세지 컨버터 추가
	// 굳이 사용할 필요 없음
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		// 메세지 컨버터를 등록하면 기본 메세지 컨버터들이 등록되지 않아 사용할 수 없음
//		// 단순 추가하려는 경우 아래 extendMessageConverters 사용
//	}
//
//	@Override
//	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		// 메세지 컨버터 추가
//	}

	// HTTP 메세지 컨버터 XML 추가
//	@Bean
//	public Jaxb2Marshaller jaxb2Marshaller() {
//		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//		// 패키지명을 설정해줘야 함
//		// Person 클래스에 @XmlRootElement 어노테이션 태깅
//		jaxb2Marshaller.setPackagesToScan(Person.class.getPackageName());
//		return jaxb2Marshaller;
//	}

	// ViewController
	// URL을 특정 뷰네임으로 매핑할 때 컨트롤러에서 핸들러를 직접 구현하지 않고 아래와 같이 ViewController 설정
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/hi").setViewName("hi");
//	}
}
