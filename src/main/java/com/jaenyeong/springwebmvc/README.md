# 서블릿
### 톰캣 설정
* Edit Run configuration > Tomcat Server 추가 후 선택 > Deployment 탭 > war exploded 추가 및 기타 설정
* Edit Run configuration
  * Deployment 탭
    * Application context 값 '/' 설정
  * Server 탭
    * URL 값 http://localhost:8080/ (Deployment 탭의 Application context 경로와 URL 마지막 경로 동일여부 확인)
  * Before launch > Build, Build '${Study_Spring-web-mvc : war exploded}' artifact 설정 여부 확인
* 터미널 실행
  * 실행 가능한 모든 파일에게 실행 권한 부여
    * 해당 톰캣 디렉토리 위치로 이동
    * chmod +x ./*.sh 실행

#### 리스너, 필터
* 서블릿 리스너
  * 서블릿 컨텍스트 수준 이벤트
    * 컨텍스트 라이프사이클 이벤트
    * 컨텍스트 애트리뷰트 변경 이벤트
  * 세션 수준 이벤트
    * 세션 라이프사이클 이벤트
    * 세션 애트리뷰트 변경 이벤트
* 서블릿 필터
  * 들어온 요청을 서블릿으로 보내고, 서블릿이 작성한 응답을 클라이언트로 보내기 전에 특별한 처리가 필요한 경우 사용 가능
  * 체인 형태, web.xml에 정의된 순서대로 실행

#### Spring web-mvc 의존성 추가 설정
* index.jsp 파일을 못찾거나 DispatcherServlet 클래스 예외 발생시 설정 확인
  * 404 Not Found
  * 500 ClassNotFoundException: org.springframework.web.servlet.DispatcherServlet
* maven, gradle에서 Spring web-mvc 의존성 추가시 설정
  * Project Structure (command + ;) 설정
    * Artifact 탭
      * output layout 탭에 Available Element 영역에 Spring web-mvc 라이브러리를 추가 (WEB-INF 아래 lib 경로에 추가)

#### Bean 등록
* @Controller
  * DispatcherServlet이 만든 applicationContext(서브)에 등록되어야 함
* @Service
  * ContextLoaderListener가 만든 applicationContext(슈퍼)에 등록되어야 함
  
#### DispatcherServlet
* HandlerMapping : 핸들러를 찾아주는 인터페이스 (Strategy pattern으로 되어있음)
* HandlerAdapter : 핸들러를 실행하는 인터페이스
* HandlerExceptionResolver
* ViewResolver

#### 처리 순서
* 요청 분석
* 핸들러 맵핑이 처리할 핸들러를 찾음
* 기존에 등록되어 있는 핸들러 어댑터 중에서 핸들러를 실행할 핸들러 어댑터를 찾음
* 찾아낸 핸들러 어댑터를 통해 핸들러의 응답처리
  * 핸들러의 리턴값을 확인하여 처리 방법 판단
    * 뷰 이름을 찾아(뷰 리졸버) 모델 데이터를 렌더링
    * @ResponseEntity가 있다면 Converter를 사용해 응답 본문 생성
  * 예외 발생시 예외 처리 핸들러에 요청 처리를 위임
* 최종 응답

#### 구성 요소
* 기본 전략
  * DispatcherServlet.properties 참조
* MultipartResolver
  * 파일 업로드 요청 처리 관리 인터페이스
  * 구현체
    * CommonMultipartResolver(아파치 커먼스)
    * StandardServletMultipartResolver(서블릿 3.0 API)
  * HttpRequestServlet를 MultipartHttpRequestServlet으로 변환 처리
  * 기본적으로 세팅되지 않음(부트는 세팅됨)
* LocaleResolver
  * 클라이언트의 요청이 어디 지역에서 왔는지 지역 정보를 확인하는 인터페이스
  * 요청을 분석하는 단계에서 사용됨
  * 구현체 : AcceptHeaderLocalResolver(기본)
* ThemeResolver
  * 설정 테마를 변경하는 인터페이스
  * 구현체
    * AbstractThemeResolver
    * CookieThemeResolver
    * FixedThemeResolver(기본 - 사실상 사용하지 않는 것과 다름 없음)
    * SessionThemeResolver
* HandlerMapping (다수의 빈 사용)
  * 클라이언트 요청을 처리할 핸들러를 찾는 인터페이스
  * 구현체
    * RequestMappingHandlerMapping(기본)
    * BeanNameUrlHandlerMapping
* HandlerAdapter (다수의 빈 사용)
  * 핸들러 매핑이 찾아낸 핸들러를 실행할 인터페이스
  * 스프링 MVC 확장력의 핵심
  * 구현체
    * RequestMappingHandlerAdapter(기본)
    * SimpleControllerHandlerAdapter
    * HttpRequestHandlerAdapter
* HandlerExceptionResolver (다수의 빈 사용)
  * 클라이언트 요청 처리 중에 발생한 에러를 처리하는 인터페이스
  * 구현체
    * ExceptionHandlerExceptionResolver(기본)
    * ResponseStatusExceptionResolver
    * DefaultHandlerExceptionResolver
* RequestToViewNameTranslator
  * 핸들러에서 뷰네임을 명시적으로 반환하지 않은 경우에 클라이언트 요청을 기반으로 뷰네임을 판단하는 인터페이스
  * 구현체
    * DefaultRequestToViewNameTranslator
* ViewResolver (다수의 빈 사용)
  * 뷰네임에 해당하는 뷰를 찾아내는 인터페이스
  * 구현체
    * InternalResourceViewResolver(기본)
* FlashMapManager
  * FlashMap 인스턴스를 가져오고 저장하는 인터페이스
    * 클라이언트 요청 데이터를 받아 저장한 후 리다이렉트
    * 새로고침 같은 리다이렉트 할 때 form submit과 같은 post 요청시 중복 요청과 방지, 동일 데이터를 다시 받아오지 않게 하는 패턴
    * form submit 같은 Post 요청을 받았을 때 리다이렉션 후 get 요청으로 리다이렉트
    * post요청의 중복을 방지하기 위한 패턴
  * 구현체
    * SessionFlashMapManager

#### 초기화
* 특정 타입에 해당하는 빈을 찾음
* 없으면 기본 전략 사용 (DispatcherServlet.properties)

#### 스프링
* 서블릿 컨테이너(톰캣, 제티, 제우스 등)에 등록한 웹앱(War)에 DispatcherServlet을 등록
  * web.xml에 서블릿 등록
  * WebApplicationInitializer에 자바 코드로 서블릿 등록 (스프링 3.1, 서블릿 3.0)
  * 세부 구성 요소는 빈 설정에 따라 달라짐

#### 스프링 부트
* 자바 앱에 내장 톰캣을 생성, 그 안에 DispatcherServlet을 등록
  * 스프링 부트 자동 설정이 자동으로 해줌
* 여러 인터페이스 구현체를 자동으로 등록

#### 스프링 WEB-MVC 설정
* Web.xml 사용
  * 파일에 직접 빈과 같은 설정 등록
* Java 소스 작성하여 설정
  * 직접 빈 등록
    * @Bean
  * @EnableWebMvc 사용
    * 빈을 자동으로 등록해줌
      * 일반적으로 어노테이션 관련 객체들을 사용
        * 성능상 더 효과적
    * 설정 방법
      * WebApplicationInitializer를 구현한 클래스 설정 (WebApplication 클래스)
        * ``` context.setServletContext(servletContext); ```와 같이 ApplicationContext에 ServletContext 설정
      * @Configuration 태깅된 클래스에 @EnableWebMvc 어노테이션 태깅 (WebConfig 클래스)
        * WebMvcConfigurer(스프링 3.1) 인터페이스 구현
          * 확장 지점을 위해 구현
          * 스프링 부트에서도 자주 사용됨
          * 포매터 등은 빈으로 등록되어 있는 경우 직접 설정하지 않아도 자동으로 레지스트리에 등록되어 사용 가능
          * 인터셉터 등은 직접 등록 필요(확실하지 않음)
    * DelegatingWebMvcConfiguration을 읽어옴
      * 델리게이션 구조(위임)
      * 기존 빈에 조금만 수정하는 방식으로 사용 가능

#### 스프링 부트 기본 설정
* handlerMapping
  * SimpleUrlHandlerMapping
    * faviconHandlerMapping
  * RequestMappingHandlerMapping(우선순위 0)
  * BeanNameUrlHandlerMapping
  * SimpleUrlHandlerMapping
    * resourceHandlerMapping(정적 리소스 지원)
  * WelcomePageHandlerMapping
* handlerAdapters
  * RequestMappingHandlerAdapter(어노테이션 기반)
  * HttpRequestHandlerAdapter
  * SimpleControllerHandlerAdapter
* viewResolvers
  * ContentNegotiatingViewResolver가 다른 뷰리졸버에게 역할을 위임
  * BeanNameViewResolver
  * ThymeleafViewResolver
  * ViewResolverComposite
  * InternalResourceViewResolver
* 스프링 부트의 주관이 적용된 자동 설정이 동작
  * JSP 보다는 thymeleaf 선호
  * JSON 지원
  * 정적 리소스 지원
  
#### 스프링 부트 MVC 설정
* spring-boot-autoconfigure.jar
  * spring.factories 파일 참조
  * WebMvcAutoConfiguration 클래스 (스프링 자동 설정)
    * @ConditionalOnWebApplication(type = TYPE.NONE, TYPE.SERVLET, TYPE.WEBFLUX)
      * 앱 타입 설정
    * @ConditionalOnClass(Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class)
      * 위 클래스들이 클래스 패스에 있을 때 사용
    * @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
      * WebMvcConfigurationSupport 타입의 빈이 없는 경우에만 WebMvcAutoConfiguration를 사용
      * @EnableWebMvc를 사용하면 WebMvcConfigurationSupport 타입의 빈이 생성되어 WebMvcAutoConfiguration를 사용하지 않게 됨
* 스프링 MVC 커스터마이징
  * application.properties
    * 가능하면 이 방법을 선호
  * @Configuration + implements WebMvcConfigurer
    * 스프링 부트의 스프링 MVC 자동설정 + 추가설정
  * @Configuration + @EnableWebMvc (+ WebMvcConfigurer 구현)
    * 스프링 MVC 자동 설정 사용하지 않을 때 사용

#### 스프링부트 JSP 사용
* 제약 사항
  * JAR 프로젝트로 만들 수 없음, WAR 프로젝트로 만들어야 함
  * Java -JAR로 실행할 수는 있지만 “실행가능한 JAR 파일”은 지원하지 않음
  * 언더토우(JBoss에서 만든 서블릿 컨테이너)는 JSP를 지원하지 않음
  * Whitelabel 에러 페이지를 error.jsp로 오버라이딩 할 수 없음
* Spring boot 설정
  * 의존성 추가
    * implementation group: 'javax.servlet', name: 'jstl', version: '1.2'
    * compileOnly group: 'org.apache.tomcat.embed', name: 'tomcat-embed-jasper', version: '9.0.36'
  * 의존성 제거
    * implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    * compileOnly 'org.projectlombok:lombok'
* maven 프로젝트로 생성하여 톰캣에 war 파일 배포 실행은 되나 gradle 프로젝트에서 실패
  * provided 문제로 예상

#### 스프링 부트 설정
* 작성한 포매터를 빈으로 등록시 직접 웹 설정에서 등록하지 않아도 올바르게 작동
* 스프링 데이터 JPA
  * 도메인 클래스 컨버터 기능
    * @Entity 어노테이션과 속성 @Id, @GeneratedValue 등과 같은 어노테이션을 태깅하여 사용시 별도의 컨버터를 구현하지 않아도 됨
    * 실제 적용시 스프링 부트 2.3.1 버전에서 MethodArgumentConversionNotSupportedException 예외 발생
    * 따라서 스프링 부트 버전을 2.3.0으로 다운그레이드

#### 핸들러 인터셉터
* 핸들러 매핑에 설정 - 핸들러 매핑이 찾아준 핸들러에 인터셉터를 적용
* 순서
  * preHandle1 - 메세지 출력 등 전처리 작업
  * preHandle2 - 메세지 출력 등 전처리 작업
  * 요청 처리
  * postHandle2 - 후처리 작업
  * postHandle1 - 후처리 작업
  * 뷰 렌더링
  * afterCompletion2 - 최종 처리 작업
  * afterCompletion1 - 최종 처리 작업
* preHandle
  * 핸들러 실행 전 호출
  * 핸들러에 대한 정보를 제공해주기 때문에 세밀한 작업 가능
  * boolean 타입 반환, false면 다음 핸들러 인터셉터나 핸들러에게 전달되지 않고 바로 응답처리
* postHandle
  * 핸들러 처리 후 뷰 렌더링 전 호출
  * modelAndView를 제공해주기 때문에 뷰에 전달할 추가 데이터 처리 등이 가능, 뷰 변경 가능
  * 인터셉터 역순으로 호출
  * 비동기 요청 처리시 호출되지 않음
* afterCompletion
  * 뷰 렌더링 후 호출
  * preHandle에서 false 반환시 실행 안됨
  * 인터셉터 역순으로 호출
  * 비동기 요청 처리시 호출되지 않음
* 서블릿 필터와 차이
  * 핸들러 인터셉터는 좀 더 구체적인 처리 가능
  * 서블릿 필터보다 일반적인 용도의 기능을 구현하는데 사용하는 게 좋음
* Lucy xss
  * 네이버에서 만든 xss filter

#### 리소스 핸들러
* 디폴트 서블릿
  * 서블릿 컨테이너가 기본으로 제공하는 서블릿
  * 정적인 리소스를 처리할 때 사용
* 우선순위가 가장 낮은 핸들러로 등록됨

#### HTTP 메세지 컨버터
* 요청 본문에서 메시지를 읽어들이거나(@RequestBody), 응답 본문에 메시지를 작성할 때(@ResponseBody) 사용
* 기본 HTTP 메시지 컨버터
  * 바이트 배열 컨버터
  * 문자열 컨버터
  * Resource 컨버터
  * Form 컨버터 (폼 데이터 to/from MultiValueMap<String, String>)
  * (JAXB2 컨버터)
  * (Jackson2 컨버터)
  * (Jackson 컨버터)
  * (Gson 컨버터)
  * (Atom 컨버터)
  * (RSS 컨버터)
  * 기타
* 설정
  * configureMessageConverters - 기존 컨버터들을 사용하지 않고 새로 등록
  * extendMessageConverters - 기존 컨버터들에 새 컨버터를 추가
  * 의존성 추가
    * 빌드툴에 의존성 추가하면 컨버터가 자동으로 추가됨
    * WebMvcConfigurationSupport - 스프링 부트가 아닌 스프링

#### HTTP 메세지 컨버터 JSON
* 의존성에 따라 조건적으로 등록됨
  * Gson, JacksonJSON, JacksonJSON2 등
* 스프링 부트를 사용하는 경우
  * 기본적으로 JacksonJSON2가 등록되어 있음

#### HTTP 메세지 컨버터 XML
* OXM(Object-XML Mapper) 라이브러리 중에 스프링이 지원하는 의존성 추가
  * JacksonXML
  * JAXB
* 스프링 부트를 사용하는 경우
  * 기본적으로 추가되어 있지 않기 때문에 수동으로 의존성 추가해야 함
* JAXB 추가
  * HTTP 메세지 컨버터 XML 인터페이스
    * implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1'
  * HTTP 메세지 컨버터 XML 구현체
    * implementation group: 'org.glassfish.jaxb', name: 'jaxb-runtime', version: '2.3.2'
  * 마샬링, 언마샬링
    * implementation group: 'org.springframework', name: 'spring-oxm', version: '5.2.7.RELEASE'

#### WebConfigurer
* CORS
  * Cross Origin 설정
* ArgumentResolver
  * 핸들러에서 사용하는 아규먼트에 대한 리졸버
  * 핸들러에서 사용할 아규먼트를 커스터마이징해 사용할 때 아규먼트 리졸버를 추가해 설정
* ReturnValueHandler
  * 핸들러가 반환하는 값을 처리하는 핸들러 
  * 기본적으로 제공하는 핸들러가 반환하는 값 이외에 값을 커스터마이징 할 때 추가해 설정
* ViewController
  * URL을 특정 뷰네임으로 매핑할 때 추가해 설정
* Async
  * 비동기처리시 사용하는 thread executor 풀 개수, 타임 아웃 등 설정
* ContentNegotiation
  * 기본적으로 요청 헤더, 어셉트를 보고 판단
  * 예를 들어 localhost:8080/hello.json 와 같이 처리하고 싶을 때

#### 설정 정리
* @EnableWebMvc
  * 스프링 기본 설정 사용
  * 해당 어노테이션 태깅시 스프링 부트의 설정은 사용 못함
  * WebMvcConfigurer를 상속하여 제공하는 기능을 사용 및 커스터마이징
* 설정이 많고 까다로움
  * 핸들러 매핑 설정시 안에 인터셉터도 설정 해야함
  * 핸들러 어댑터 설정시 안에 메세지 컨버터도 설정 해야함
* 스프링 부트 사용시
  * application.properties(YAML) 파일에 spring.mvc 등 추가 커스터마이징 설정 가능
  * 위 설정으로 커스터마이징이 힘든 경우 WebMvcConfigurer를 상속하여 추가 커스터마이징 설정
  * 그 외에 필요시 @Bean으로 설정 빈 등록하여 사용
-----
