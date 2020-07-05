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

### 리스너, 필터
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

### Spring web-mvc 의존성 추가 설정
* index.jsp 파일을 못찾거나 DispatcherServlet 클래스 예외 발생시 설정 확인
  * 404 Not Found
  * 500 ClassNotFoundException: org.springframework.web.servlet.DispatcherServlet
* maven, gradle에서 Spring web-mvc 의존성 추가시 설정
  * Project Structure (command + ;) 설정
    * Artifact 탭
      * output layout 탭에 Available Element 영역에 Spring web-mvc 라이브러리를 추가 (WEB-INF 아래 lib 경로에 추가)

### Bean 등록
* @Controller
  * DispatcherServlet이 만든 applicationContext(서브)에 등록되어야 함
* @Service
  * ContextLoaderListener가 만든 applicationContext(슈퍼)에 등록되어야 함
  
### DispatcherServlet
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
* 서블릿 컨테이너(톰캣, 제우스 등)에 등록한 웹앱(War)에 DispatcherServlet을 등록
  * web.xml에 서블릿 등록
  * WebApplicationInitializer에 자바 코드로 서블릿 등록 (스프링 3.1, 서블릿 3.0)
  * 세부 구성 요소는 빈 설정에 따라 달라짐

#### 스프링 부트
* 자바 앱에 내장 톰캣을 생성, 그 안에 DispatcherServlet을 등록
  * 스프링 부트 자동 설정이 자동으로 해줌
* 여러 인터페이스 구현체를 자동으로 등록

