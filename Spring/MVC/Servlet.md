# MVC

### hello.servlet.ServletApplication.java

```java
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {
    ...
}
```

> @ServletComponentScan : 서블릿컴포넌트(필터, 서블릿, 리스너)를 스캔해서 빈으로 등록한다.
> <br>서블릿컴포넌트를 그냥 등록하는게 아니고 해당 서블릿컴포넌트 클래스에는 아래와 같이 별도의
> 어노테이션이 추가되어 있어야 한다.

---

### hello.servlet.basic;

```java


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);

    }
}

```

### @WebServlet 서블릿 애노테이션

- name: 서블릿 이름
- urlPatterns: URL 매핑

![image](https://user-images.githubusercontent.com/90185805/161962801-d497c48f-fabc-441e-a821-e027e82bd6f7.png)
![image](https://user-images.githubusercontent.com/90185805/161962863-cb362d48-1fe0-4d88-a0a4-8d4a2bde34ff.png)

---

## Welcome page 만들기

### main/webapp/index.html

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Title</title>
  </head>
  <body>
    <ul>
      <li><a href="basic.html">서블릿 basic</a></li>
    </ul>
  </body>
</html>
```

---

## HttpServletRequest의 역할

> HTTP 요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편할 것이다. 서블릿은 개발자가
> HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱한다. 그리고 그
> 결과를 HttpServletRequest 객체에 담아서 제공한다

예)

```html
POST /save HTTP/1.1 Host: localhost:8080 Content-Type:
application/x-www-form-urlencoded username=kim&age=20
```

- START LINE
  - HTTP 메소드
  - URL
  - 쿼리 스트링
  - 스키마, 프로토콜
- 헤더
  - 헤더 조회
- 바디
  - form 파라미터 형식 조회
  - message body 데이터 직접 조회

## HttpServletRequest 객체는 추가로 여러가지 부가기능도 함께 제공한다

### 임시 저장소 기능

- 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
  - 저장: request.setAttribute(name, value)
  - 조회: request.getAttribute(name)
- 세션 관리 기능
  `request.getSession(create: true)`
- 중요
  > HttpServletRequest, HttpServletResponse를 사용할 때 가장 중요한 점은 이 객체들이 HTTP 요청

---

## HTTP 요청 데이터 - 개요

### HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법을 알아보자.

- GET - 쿼리 파라미터

  ```html
  /url?username=hello&age=20
  ```

  > 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달 <br>
  > 예) 검색, 필터, 페이징등에서 많이 사용하는 방식

  ```java
  /**
    * 1. 파라미터 전송 기능
    * http://localhost:8080/request-param?username=hello&age=20
    */
  @WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
  public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[전체 파라미터 조회] - start");

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));

        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();

        // http://localhost:8080/request-param?username=hello&age=20&username=hello1
        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }
        response.getWriter().write("ok");
    }
  }
  ```

- POST - HTML Form

  - content-type: application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20
  - 예) 회원 가입, 상품 주문, HTML Form 사용

  ![image](https://user-images.githubusercontent.com/90185805/162175998-76638032-ad09-4201-8965-586738ae7224.png)

  #### request.getParameter() 는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.

- HTTP message body에 데이터를 직접 담아서 요청

  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH

  ```java
  @WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
  public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");
    }
  }
  ```

- http://localhost:8080/request-body-string body에 값 넣기

---

## HTTP 요청 데이터 - API 메시지 바디 - JSON

- JSON 형식 전송
- POST http://localhost:8080/request-body-json
- content-type: application/json
- message body: {"username": "hello", "age": 20}
- 결과: messageBody = {"username": "hello", "age": 20}

### hello.servlet.basic

```java
@Getter @Setter
public class HelloData {
  private String username;
  private int age;
}
```

### hello.servlet.basic.request

```java
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // JSON 변환 라이브러리 추가
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println("helloData = " + helloData.getUsername());
        System.out.println("helloData = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}
```

### postMan으로 데이터 보내기

```json
{ "username": "hello", "age": 20 }
```

## HttpServletResponse - 기본 사용법
