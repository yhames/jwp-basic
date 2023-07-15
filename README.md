#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* `ServletContext` 생성 : 서블릿 컨테이너가 자동으로 생성
* `ServletContext` 초기화 : `@WebListener`이 붙은 `ServletContainerListener` 구현체의 `contextInitialized()` 메서드 호출
  * `jwp.sql`파일의 `SQL`문을 실행하여 `DB` 테이블 초기화 
* `Servlet 인스턴스` 생성 : `Servlet 구현체`를 조회하여 `DispatcherServlet`를 컨테이너에 등록
  * `@WebServlet`의 `urlPattern` 속성으로 `url` 매핑
    * `/`으로 설정하면 모든 요청 처리
    * `ResourceFilter`를 사용하여 `css`, `js` 등 정적자원은 `defaultRequestDispatcher`이 처리하도록 설정
  * 클라이언트 요청시 서블리 인스턴스 생성(생성자 호출) 및 초기화(`init()` 메서드 호출)
    * `@WebServlet`의 `loadOnStartup`를 설정하여 컨테이너 시작할 때 생성 및 초기화 
* `DispatcherServlet`의 `init()` 메서드를 통해 `RequestMapping` 객체 생성 및 초기화
  * `RequestMapping` 인스턴스의 `initMapping()` 메서드를 호출하여 `요청 URL`과 `Controller 인스턴스` 매핑

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트 요청시 먼저 `ServletFilter` 호출
  * `ResourceFilter`: 정적자원 요청시 `defaultRequestDispatcher`로 처리
  * `CharacterEncodingFileter`: 모든 `request`의 `encoding`을 `UTF-8`로 설정
* `/`이 매핑되어있는 `DispatcherServlet`의 `service()` 메서드 호출
  * `RequestMapping`에서 `요청 URL`에 해당되는 `Controller` 가져옴
* 가져온 `Controller`의 `excute()` 메서드 실행
* `excute()`의 반환값 `ModelAndView`의 `model` 데이터를 `view`의 `render()` 메서드에 전달
* `render()` 메서드를 실행하여 모델데이터를 `jsp`파일에 전달하여 `HTML` 생성 및 응답 데이터를 클라이언트에 전달

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* ShowController는 싱글톤으로 사용되고 있음
* question과 answers가 필드에 선언되면, 멀티스레드에서 필드가 공유되서 서로 충돌이 일어날 수 있음
* 그래서 지역변수로 선언함

> 왜 인스턴스의 필드값은 스레드 간 공유되는가?
> * JVM은 메모리를 `static`, `stack`, `heap`으로 나눠서 관리함.
`stack`영역은 매개변수, 지역번수 등을 관리하는 영역으로 각 스레드마다 할당되는
반면 `heap`영역은 인스턴스의 상태 데이터(필드값)를 관리하며 스레드 간 공유되어 사용됨.
> * JVM은 메서드가 호출되면 각 메서드 별 스택 프레임을 생성하는데
메서드의 지역변수 영역의 첫 번째에 자기 자신에 대한 메모리 위치를 가리키는 `this`가 할당됨.
클래스의 인스턴스는 `heap`영역에 생성되어 있으므로 `this`는 `heap`영역의 인스턴스를 가리키고,
인스턴스의 필드값은 heap 영역의 필드값을 가리킴.
> * 따라서 필드값은 스레드 간 공유되는 값으로
`스레드1`에서 사용중인 상황에서 `스레드2`가 필드값을 수정하면 `스레드1`은 `스레드2`의 값을 받게됨.
