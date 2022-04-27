# Thymeleaf 특징

- 타임리프는 서버 사이드 HTML을 랜더링한다.
- 백엔드 서버에서 HTML을 동적으로 렌더링 처리해준다.
- 타임리프는 `서버에서 받아온 데이터를 ${ }` 을 이용하여 표기한다.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <!-- 여기  -->

  <head>
    <meta charset="UTF-8" />
    <title>Title</title>
  </head>
  <body></body>
</html>
```

> 타임리프를 적용하기 위해서 html 태그의 시작 부분에 `xmlns:th="http://www.thymeleaf.org`을 태그 내부에 작성한다.

# 자주 사용하는 문법

- 간단한 표현
  - 변수 표현식 : ${}
  - 선택 변수 표현식 : \*{}
  - 메세지 표현식 : #{}
  - 링크 URL 표현식 : @{}
  - 조각 표현식 : ~{}
- 문자 연산
  - 문자 합치기 +
    m- 리터럴 대체 : | hi, y name is ${name}|
- 조건 연산
  - if-Then: (if) ? (then)
  - if-Then-else: (if) ? (then) : (else)
  - default: ( value) ?: (defaultValue)

| 문법    | 역할        | 예제                                     |
| ------- | ----------- | ---------------------------------------- |
| th:text | 문자열 생성 | th:text=" ${data} "                      |
| th:each | 반복문      | th:each="article : ${articleList}"       |
| th:if   | if 조건문   | th:if=${data != null}                    |
| th:href | 이동 경로   | th:href=" @{/article/list(id= ${data})}" |
