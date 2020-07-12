# Study_Spring-Web-MVC
### 인프런 스프링 웹 MVC (백기선)
https://www.inflearn.com/course/%EC%9B%B9-mvc/dashboard

-----

### [Settings]
#### Project Name
* Study_Spring-web-mvc
#### java
* zulu jdk 11
#### gradle
* IDEA gradle wrapper
#### Spring boot
* 2.3.1

-----

## [Contents]
### 서블릿 톰캣 설정
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

## 스프링 핵심 기술
* 요청 맵핑하기
* 핸들러 메소드
* 모델과 뷰
* 데이터 바인더
* 예외 처리
* 글로벌 컨트롤러

### 적용된 기술
* 스프링 부트
* 스프링 웹 MVC
* 타임리프

### HTTP 요청 매핑
* @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
* @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping 등등

#### HTTP 메서드
* GET
  * 클라이언트가 서버의 리소스를 요청할 때 사용
  * 캐싱 가능 (조건적인 GET으로 바뀔 수 있음)
  * 브라우저 기록에 남음
  * 북마크 할 수 있음
  * 민감한 데이터를 보낼 때 사용하지 말 것 (URL에 다 보임)
  * idempotent (멱등성)
* POST
  * 수정 또는 새로 생성 요청시 사용
  * 서버에 보내는 데이터를 POST 요청 본문에 담음
  * 캐시 불가능
  * 브라우저 기록에 남지 않음
  * 북마크 할 수 없음
  * 데이터 길이 제한이 없음
  * idempotent (멱등성) 하지 않을 수 있음
* PUT
  * URI에 해당하는 데이터(리소스)를 생성, 수정할 때 사용
  * POST와 URI의 의미가 다름
    * POST의 URI는 보내는 데이터를 처리할 리소스(서비스)를 지칭
    * PUT의 URI는 보내는 데이터에 해당하는 리소스(데이터 자체)를 지칭
  * idempotent (멱등성)
* PATCH
  * PUT과 비슷
  * 기존 엔티티와 새 데이터의 차이점만 보낸다는 차이가 있음
  * idempotent (멱등성)
* DELETE
  * 해당 리소스(데이터)를 삭제할 때 사용
  * idempotent (멱등성)

#### URI, URL, URN 정의
* URI (Uniform Resource Identifier)
  * 통합 자원 식별자
  * 최상위 개념 (URL, URN을 포함하는 개념)
* URL (Uniform Resource Locator)
  * 인터넷 상에 리소스의 위치를 알려주기 위한 규약, 문자
  * 일반적으로 주소의 위치까지 나타내는게 URL (쿼리스트링 등은 미포함)
  * 현재 URI 중 가장 많이 쓰이는 형태 (현재 대부분 상용 서비스의 리소스 URI는 URL의 형태)
* URN (Uniform Resource Name)
  * 실제 파일 위치 상관없이 리소스의 이름을 사용한 식별자
  * 위치 변경에 무관

#### URI 패턴 매핑
* @RequestMapping 지원 패턴
  * ``` ? ``` 
    * 한 글자 (“/author/???” => “/author/123”)
  * ``` * ``` 
    * 여러 글자 (“/author/*” => “/author/jaenyeong”)
  * ``` ** ```
    * 여러 패스 (“/author/** => “/author/jaenyeong/book”)
  * 정규 표현식
    * "/exp/{name:[a-z]+}"
  * 중복 URL 매핑
    * 가장 구체적인 핸들러와 매핑됨
  * URI 확장자 매핑 지원
    * "/jaenyeong.*"
    * 사용을 권장하지 않음
      * 보안이슈 (RFD 공격)
      * URI 변수, Path 매개변수, URI 인코딩을 사용할 때 할 때 불명확
        * 예전에는 클라이언트가 원하는 콘텐츠 타입 등을 위해서 확장자 패턴을 사용했었음
        * 최근에는 요청 헤더에 어셉트 타입 명시 또는 쿼리스트링과 같은 파라미터(요청 매개변수) 이용
    * 스프링 부트에서는 기본적으로 이 기능을 사용하지 않도록 설정되어 있음
    
#### content type(송신 타입), accept(원하는 리턴 타입)
* @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  * 문자열로 설정할 수 있으나 가급적 MediaType 상수를 사용해 설정하는 것을 추천
  * content type 설정 : consumes
    * 서버, 클라이언트 둘다 MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE 타입 상관 없음
  * accept 설정 : produces
    * 요청 헤더에 accept가 지정되어 있지 않는 경우에는 서버 핸들러에 accept가 지정되어 있더라도 매핑이 문제 없이 됨
  * @RequestMapping(value = "/**") 와 같은 전처리 핸들러가 없다는 가정하에 (요청 핸들러 이외에 매핑되는 핸들러가 없는 경우에)
    * content type 맞지 않는 경우 415(Unsupported Media) 에러 반환
    * accept 맞지 않는 경우 406(Not acceptable) 에러 반환
  * consumes, accept 둘 다 클래스(컨트롤러) 수준에서도 설정 가능
    * 하지만 핸들러(메서드)에서 선언한 설정이 있는 경우 오버라이딩을 해서 중복 적용이 되지 않고 핸들러(메서드) 설정만 적용됨
    * 여러 개를 중복 적용하려면 핸들러(메서드)에 배열로 정의
  * !(Not) 사용해 특정 미디어 타입이 아닌 경우로도 매핑 가능

#### headers 설정
* @RequestMapping(value = "/headers", headers = HttpHeaders.FROM)
* 특정한 헤더가 있는 요청을 처리하고 싶은 경우
  * @RequestMapping(headers = "key")
* 특정한 헤더가 없는 요청을 처리하고 싶은 경우
  * @RequestMapping(headers = "!key")
* 특정한 헤더 키/값이 있는 요청을 처리하고 싶은 경우
  * @RequestMapping(headers = "key=value")
* 특정한 요청 매개변수 키를 가지고 있는 요청을 처리하고 싶은 경우
  * @RequestMapping(params = "a")
* 특정한 요청 매개변수가 없는 요청을 처리하고 싶은 경우
  * @RequestMapping(params = "!a")
* 특정한 요청 매개변수 키/값을 가지고 있는 요청을 처리하고 싶은 경우
  * @RequestMapping(params = "a=b")
* "/headers?name=jaenyeong"과 같은 파라미터도 설정 가능
  * 조건이 맞지 않으면 400(Bad request) 에러 발생

#### HEAD, OPTIONS HTTP 메서드
* 직접 구현하지 않아도 스프링에서 자동으로 처리하는 HTTP 메서드
* HEAD 메서드
  * GET 요청과 동일하지만 응답 본문을 받아 오지 않고 응답 헤더만 수신
  * param 설정시 요청이 HEAD 메서드라도 param이 있어야 함
* OPTION 메서드
  * 사용할 수 있는 HTTP 메서드 목록 제공
  * 서버, 특정 리소스가 제공하는 기능 확인 가능
  * 서버는 ALLOW 응답 헤더에 지원하는 HTTP 메서드 목록을 제공

#### 커스텀 어노테이션
* @Documented
  * java doc과 같은 유형으로 문서화
* @Target(ElementType.METHOD)
  * 사용할 곳(컨텍스트) 지정
* @Retention(RetentionPolicy.RUNTIME)
  * 어노테이션 유지 기간 옵션 지정
  * Source
    * 소스 코드까지만 유지 (컴파일 후 컴파일된 .class 파일에는 포함되지않음)
  * Class
    * 바이트코드인 .class 파일까지 유지 (런타임 시 메모리에 로딩되지 않음)
  * Runtime
    * 메모리에 기존 소스와 같이 로딩됨
* 메타(Meta) 애노테이션
  * 애노테이션에 사용할 수 있는 애노테이션 (위 어노테이션과 같은 것들)
  * 스프링이 제공하는 대부분의 애노테이션은 메타 애노테이션으로 사용 가능
* 합성(Composite) annotation
  * 한개 혹은 여러 메타 애노테이션을 조합해서 만든 애노테이션
  * 코드를 간결하게 줄일 수 있음
  * 보다 구체적인 의미를 부여할 수 있음

### 핸들러 메서드

#### 아규먼트, 리턴 타입
* 요청 또는 응답 자체에 접근 가능한 API
  * WebRequest
  * NativeWebRequest
  * ServletRequest(Response)
  * HttpServletRequest(Response)
* 요청 본문을 읽어오거나, 응답 본문을 쓸 때 사용할 수 있는 API
  * InputStream
  * OutputStream
  * Reader
  * Writer
* 스프링 5, HTTP/2 리소스 푸쉬에 사용
  * PushBuilder
* GET, POST, ... 등에 대한 정보
  * HttpMethod
* LocaleResolver가 분석한 요청의 Locale 정보
  * Locale
  * TimeZone
  * ZoneId
* URI 템플릿 변수 읽을 때 사용
  * @PathVariable
* URI 경로 중에 키/값 쌍을 읽어 올 때 사용
  * @MatrixVariable
* 서블릿 요청 매개변수 값을 선언한 메소드 아규먼트 타입으로 변환. 단순 타입인 경우에 이 애노테이션을 생략 가능
  * @RequestParam
* 요청 헤더 값을 선언한 메소드 아규먼트 타입으로 변환
  * @RequestHeader
* 리턴 값을 HttpMessageConverter를 사용해 응답 본문으로 사용
  * @ResponseBody
* 응답 본문 뿐 아니라 헤더 정보까지, 전체 응답을 만들 때 사용
  * HttpEntity
  * ResponseEntity
* ViewResolver를 사용해서 뷰를 찾을 때 사용할 뷰네임
  * String
* 암묵적인 모델 정보를 랜더링할 뷰 인스턴스
  * View
* (RequestToViewNameTranslator를 통해서) 암묵적으로 판단한 뷰 랜더링할 때 사용할 모델 정보
  * Map
  * Model
* (RequestToViewNameTranslator를 통해서) 암묵적으로 판단한 뷰 랜더링할 때 사용할 모델 정보에 추가. 이 애노테이션은 생략 가능
  * @ModelAttribute

#### URI 패턴
* @PathVariable
  * 요청 URI 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법
  * 타입 변환 지원
  * (기본)값이 반드시 있어야 함
  * Optional 지원
* @MatrixVariable
  * 요청 URI 패턴에서 키/값 쌍의 데이터를 메소드 아규먼트로 받는 방법
  * 타입 변환 지원
  * (기본)값이 반드시 있어야 함
  * Optional 지원
  * 이 기능은 기본적으로 비활성화. 활성화 하려면 다음과 같이 설정해야 함
    * WebMvcConfigurer를 구현하는 설정 객체 생성(@Configuration 태깅된 객체)
    * configurePathMatch(PathMatchConfigurer configurer) 메서드 오버라이딩
      * UrlPathHelper 객체를 생성, setRemoveSemicolon 메서드 false 설정
      * 스프링 설정 객체인 PathMatchConfigurer에 setUrlPathHelper 메서드를 사용해 UrlPathHelper 객체 주입

#### @RequestMapping
* 요청 매개변수
  * queryString(쿼리 매개변수)
  * form data
* @RequestParam
  * 요청 매개변수에 들어있는 단순 타입 데이터를 메소드 아규먼트로 받아올 수 있음
  * 값이 반드시 있어야 함
  * required=false 또는 Optional을 사용해서 부가적인 값으로 설정 가능
  * String이 아닌 값들은 타입 컨버전을 지원
  * Map<String, String> 또는 MultiValueMap<String, String>에 사용해서 모든 요청 매개변수를 받아올 수 있음
  * 이 애노테이션은 생략 가능
* @RequestParam와 @ModelAttribute는 생략 가능

#### form submit
* 타임리프
  * 서블릿 컨테이너 엔진이 없어도 렌더링이 됨
  * 표현식
    * @{}: URL 표현식
    * ${}: variable 표현식
    * *{}: selection 표현식

#### @ModelAttribute
* URI 패스, 요청 매개변수, 세션 등과 같이 여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객체를 새로 만들 때 사용 가능
* 생략 가능
* 값읇 바인딩할 수 없는 경우 BindException 발생 400(Bad request) 에러
  * 바인딩 에러를 직접 다룰려는 경우
    * BindingResult 타입의 아규먼트를 바로 오른쪽에 추가
* 바인딩 이후에 검증 작업을 추가로 하고 싶은 경우
  * @Valid 또는 @Validated 애노테이션을 사용
    * @Valid
      * javax.validation.constraints 패키지
      * 그룹 지정 안됨
    * @Validated
      * org.springframework.validation.annotation 패키지
      * Validated 그룹화 인터페이스 선언하여 처리하는 경우 이벤트 생성 중 검증, 이벤트 수정 중 검증처럼 설계하는 것이 나음
* 스프링 최신 버전에서 유효성검사가 작동하지 않는 문제 등이 있어 프레임워크 버전 다운그레이드

#### @Validated
* 위에서 언급했듯 @Valid는 그룹지정이 안되기 때문에 @Validated를 사용하여 그룹 클래스 설정

#### form submit 에러 처리
* 바인딩 에러 발생 시 Model에 담기는 정보
  * Event
  * BindingResult.event
* 타임리프 사용 중 에러 발생시 렌더링
  * ``` <p th:if="${#fields.hasErrors('limit')}" th:errors="*{limit}">Incorrect data</p> ```
* Post / Redirect / Get 패턴 (PRG 패턴)
  * Post 이후에 브라우저를 리프래시 하더라도 폼 서브밋이 발생하지 않도록 하는 패턴

#### @SessionAttributes
* 컨트롤러에 태깅
* 모델 정보를 HTTP 세션에 저장해주는 어노테이션
  * HttpSession을 직접 사용할 수도 있지만 이 어노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어줌
  * @ModelAttribute는 세션에 있는 데이터도 바인딩 받음
  * 여러 화면(또는 요청)에서 사용해야 하는 객체를 공유할 때 사용
* 세션 사용 용도
  * 장바구니 데이터 경우
  * 데이터 생성시 여러 화면에 걸쳐 만드는 경우
    * 입력 받는 데이터가 많아 입력 폼을 화면을 나눠 처리한 경우
* 사용 완료시 세션 데이터를 사용한 곳에서 ``` sessionStatus.setComplete(); ``` 메서드를 통해 비우도록 알려줌
  * 비우지 않을 경우 데이터가 남아 있음
* @SessionAttribute
  * HTTP 세션에 들어있는 값 참조할 때 사용
    * HttpSession을 사용할 때 비해 타입 컨버전을 자동으로 지원하기 때문에 조금 편리
      * ``` @SessionAttribute LocalDateTime visitTime ```
         * ``` System.out.println("HttpSession : " + visitTimeSession); ```
      * ``` HttpSession httpSession ```
        * ``` LocalDateTime visitTimeSession = (LocalDateTime) httpSession.getAttribute("visitTime"); ```
    * HTTP 세션에 데이터를 넣고 빼고 싶은 경우에는 HttpSession을 사용할 것
  * 다른 어노테이션과 마찬가지로 이름 매핑
  * 세션에 담겨 있는 데이터를 해당 변수에 매핑, 바인딩해줌
  * 예제에서 VisitTimeInterceptor 인터셉터 구현하여 세션에 visitTime 데이터 삽입 후 컨트롤러(핸들러)에서 빼내 값 확인
* @SessionAttributes, @SessionAttribute 차이
  * @SessionAttributes는 해당 컨트롤러 내에서만 동작
    * 즉 해당 컨트롤러 안에서 다루는 특정 모델 객체를 세션에 넣고 공유할 때 사용
  * @SessionAttribute는 컨트롤러 밖(인터셉터 또는 필터 등)에서 만들어 준 세션 데이터에 접근할 때 사용

#### RedirectAttributes
* 리다이렉트 시 전달할 데이터를 쿼리스트링으로 명시하여 전달하는 기능 수행
  * URL에 붙을 수 있어야 하기 때문에 문자열로 변환이 가능한 데이터여야 함
* 리다이렉트시 스프링에서는 모델 내에 프리미티브 타입 데이터는 쿼리스트링으로 자동으로 연결되지만 스프링 부트는 이 기능이 기본적으로 꺼져있음
  * requestMappingHandlerAdapter 객체의 setIgnoreDefaultModelOnRedirect 메서드 설정이 true인 경우(스프링부트)  
    리다이렉트시 프리미티브 타입 데이터가 자동으로 전달되지 않음
  * 원하는 값만 전달하고 싶은 경우 RedirectAttributes를 사용하여 명시적으로 전달 가능
* application.properties 설정
  * spring.mvc.ignore-default-model-on-redirect=false
  * 설정시 http://localhost:8080/events/session/form/visitTime?name=as&limit=123 와 같이 쿼리스트링으로 연결됨
* @RequestParam, @ModelAttribute로 받을 수 있음
  * @ModelAttribute 사용시 주의점
    * 쿼리스트링 데이터를 모델 어트리뷰트 사용하여 객체에 바인딩하는 경우 세션 어트리뷰트에서 사용한 이름과 동일하면 안됨
    * 동일한 이름 사용시 세션에서 데이터를 먼저 찾아보려고 함
    * 해당 예제에서는 리다이렉트 전 세션을 비우기 때문에 모델 어트리뷰트에 매핑할 값을 찾지 못해 에러 발생
    * 따라서 모델 어트리뷰트로 값을 바인딩 받는 경우 세션에서 사용한 이름과 다른 새로운 이름을 사용할 것
      * ``` @SessionAttributes("event") ```
      * ``` @ModelAttribute Event event ``` : 세션 어트리뷰트와 이름이 동일한 경우 세션에서 먼저 찾고 없으면 에러 발생
      * ``` @ModelAttribute("newEvent") Event event ``` 이처럼 새로운 이름 사용하여 쿼리스트링의 데이터를 바인딩

#### Flash Attributes
* 리다이렉트 시 데이터 전달할 때 사용
  * 데이터가 URI에 노출되지 않음
  * 임의의 객체를 저장할 수 있음
  * 보통 HTTP 세션을 사용
* 리다이렉트 한 곳에서 처리가 완료되면 해당 데이터는 세션에서 제거됨 (1회성)
* 예제 테스트 에러
  * HTML 파일에 <meta charset="UTF-8"> 태그로 인해 아래 테스트가 에러 발생
  * meta태그를 꼭 닫아줄 것 <meta charset="UTF-8"/>
* 주의
  * 세션을 통해 데이터를 전달한다고 되어 있으나 HttpSession 객체에 담겨있지 않음
  * RedirectAttributes 객체의 addFlashAttribute 메서드를 통해 데이터를 넘기는 경우
    * ``` @ModelAttribute("flashEvent") Event sessionFlashEvent ```
    * 위와 같이 모델 어트리뷰트로 받으면 addFlashAttribute 메서드 설정을 통해 넘긴 데이터 값이 아닌  
      쿼리 스트링으로 넘어오는 데이터 값이 바인딩 됨

#### MultipartFile
* 파일 업로드에 사용하는 메서드 아규먼트
* MultipartResolver 빈이 설정되어 있어야 사용 가능 (스프링 부트 자동 설정이 해 줌)
  * 스프링에서는 기본 MultipartResolver가 설정되어 있지 않음
  * 스프링 부트 MultipartProperties 클래스 (@ConfigurationProperties 어노테이션 태깅되어 있음)
* POST multipart/form-data 요청에 들어있는 파일 참조 가능
* List<MultipartFile> 아큐먼트로 여러 파일 참조 가능
* application.properties 파일 설정
  * 파일 용량 등 설정 가능
  * spring.servlet.multipart.max-file-size= 10MB
  * spring.servlet.multipart.max-request-size = 10MB

#### ResponseEntity
* 스프링 ResourceLoader를 이용해 파일 읽어옴
* 파일 다운로드 응답 헤더에 설정할 내용
  * Content-Disposition : 사용자가 해당 파일을 받을 때 사용할 파일 이름
  * Content-Type : 어떤 파일인가
  * Content-Length : 얼마나 큰 파일인가
* 미디어 타입(파일 확장자)을 직접 지정하지 않고 자바 API를 통해 확인할 수 있음
  * 미디어 타입을 확인하는 라이브러리 또한 자바 API를 통해 타입 확인
  * 해당 예제에서는 Tika 라이브러리를 사용해 처리
    * implementation group: 'org.apache.tika', name: 'tika-core', version: '1.24.1'
    * ``` Tika tika = new Tika(); String mediaType = tika.detect(file); ```

#### @RequestBody, HttpEntity
* @RequestBody
  * 요청 본문(body)에 들어있는 데이터를 HttpMessageConverter를 통해 변환한 객체로 받아올 수 있음
  * @Valid 또는 @Validated를 사용해서 값을 검증 가능
  * BindingResult 아규먼트를 사용해 코드로 바인딩 또는 검증 에러를 확인 가능
* HttpMessageConverter
  * 스프링 MVC 설정 (WebMvcConfigurer)에서 설정 가능
  * configureMessageConverters : 기본 메시지 컨버터를 변경하는 것과 같음
    * 기본 컨버터를 사용하지 않는 것과 동일
    * 가급적 사용하지 않는 것을 추천
  * extendMessageConverters : 메시지 컨버터에 추가
  * 스프링 부트에서는 기본적으로 메세지 컨버터에 jackson이 자동 등록되어 있음
  * 메서드 아규먼트를 리졸빙할 때 핸들러 어댑터들이 등록되어 있는 컨버터들을 사용(그 중에 컨버전을 할 수 있는 컨버터에게 위임)
  * 기본 컨버터
    * WebMvcConfigurationSupport.addDefaultHttpMessageConverters
* HttpEntity
  * @RequestBody와 비슷하지만 추가적으로 요청 헤더 정보까지 사용 가능
  * HttpEntity 객체 사용시엔 @RequestBody 어노테이션 생략 가능
  * 대신 제네릭에 body 타입 지정 필요

#### @ResponseBody, ResponseEntity
* @ResponseBody
  * 데이터를 HttpMessageConverter를 사용해 응답 본문 메시지로 보낼 때 사용
  * HttpMessageConverter를 사용하여 변환
  * @RestController 사용시 자동으로 모든 핸들러 메소드에 적용 (@Controller + @ResponseBody)
* ResponseEntity
  * 응답 헤더 상태 코드 본문을 직접 다루고 싶은 경우에 사용
  * 헤더, 상태정보, 바디 데이터 바인딩 가능

#### 핸들러 메서드 정리
* @JsonView
* PushBuilder
  * 스프링 5
  * HTTP/2 (양방향 푸시 가능, 클라이언트가 요청하지 않아도 서버가 푸시 가능)

#### @ModelAttribute
* Model은 인터페이스
* ModelMap은 LinkedHashMap 클래스를 상속받은 클래스
* 컨트롤러 전체에서 사용할 Model을 컨트롤러 클래스 상단에 따로 선언해 사용 가능
  * 예제에서 HandlerMethodController_2 컨트롤러 클래스 참조
* @ModelAttribute의 다른 용법
  * @RequestMapping을 사용한 핸들러 메소드의 아규먼트에 사용
  * @Controller 또는 @ControllerAdvice를 사용한 클래스에서 모델 정보를 초기화 할 때 사용
  * @RequestMapping과 같이 사용하면 해당 메소드에서 리턴하는 객체를 모델에 넣어 줌
    * RequestToViewNameTranslator

#### @InitBinder
* DataBinder(데이터 바인더)
  * 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경할 때 사용
  * 리턴 타입은 반드시 void
* 설정
  * 바인딩 설정
    * ``` webDataBinder.setDisallowedFields(); ```
  * 포매터 설정
    * ``` webDataBinder.addCustomFormatter(); ```
  * 밸리데이터 설정
    * ``` webDataBinder.addValidators(); ```
  * 특정 모델 객체에만 바인딩 또는 밸리데이터 설정을 적용하고 싶은 경우
    * ``` @InitBinder("event") ```
* Validator 인터페이스 구현한 커스터마이징 클래스를 구현할 수 있음
* 예제에서 HandlerMethodController2, EventValidator 클래스 참조

#### @ExceptionHandler
* 특정 예외가 발생한 요청을 처리하는 핸들러 정의
  * 지원하는 메소드 아규먼트 (해당 예외 객체, 핸들러 객체, ...)
  * 지원하는 리턴 값
  * REST API의 경우 응답 본문에 에러에 대한 정보를 담아주고, 상태 코드를 설정하려면 ResponseEntity를 주로 사용
  * 가장 구체적인 익셉션이 매핑됨

#### @ControllerAdvice
* 전역 컨트롤러
  * 예외 처리, 바인딩 설정, 모델 객체를 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에 사용
    * @ExceptionHandler
    * @InitBinder
    * @ModelAttributes
* 적용 범위 지정 가능
  * 특정 애노테이션을 가지고 있는 컨트롤러에만 적용
  * 특정 패키지 이하의 컨트롤러에만 적용
  * 특정 클래스 타입에만 적용

