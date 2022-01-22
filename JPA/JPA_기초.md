# JPA (Java Persistence API)

## 자바 진영의 **ORM** 기술 표준

- **JPA는 인터페이스의 모음**

---

## ORM

- Object-relational mappgin(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑

---

## JPA 동작 방식

![JPA 동작방식](https://user-images.githubusercontent.com/90185805/150646678-9d36714c-c5bb-4a60-b496-d4a6f9f7ed13.png)

##### 이미지 참고 - [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/lecture/21683?tab=curriculum&volume=1.00&quality=auto)

### - JPA는 JAVA 애플리케이션과 JDBC 사이에서 동작한다.

### - JPA가 JDBC를 호출하여 SQL으로 변환 후 DB에서 받은 결과를 다시 반환한다.

---

## JPA 저장

![JPA 저장](https://user-images.githubusercontent.com/90185805/150647002-a0a41959-8640-4a55-8e96-e2086d2be600.png)

##### 이미지 참고 - [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/lecture/21683?tab=curriculum&volume=1.00&quality=auto)

### - Entity 분석 -> Insert SQL 생성 -> JDBC API를 사용 -> 쿼리를 DB에 보내고 결과를 받는다.

### **중요** 패러다임 불일치를 해결한다.

---

## JPA 조회

![JPA 조회](https://user-images.githubusercontent.com/90185805/150647181-cbc40cfd-7823-4ea1-a064-a9f1524ec6f8.png)

##### 이미지 참고 - [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/lecture/21683?tab=curriculum&volume=1.00&quality=auto)

### - SELECT SQL 생성 -> JDBC API를 사용 -> 쿼리를 DB에 보내고 결과를 받는다. -> ResultSet 매핑

### - **중요** 패러다임 불일치를 해결한다.

---

## JPA를 사용하는 이유

- SQL 중심적인 개발에서 객체 중심으로 개발

- 생산성
  - 저장 : `jpa.persist(member)`
  - 조회 : `Member member = jpa.find(memberId)`
  - 수정 : `member.setName("변경할 이름")`
  - 삭제 : `jpa.remove(member)`
- 유지보수

  - 기존: 필드 변경 시 모든 SQL을 수정해야 한다.
  - JPA: **필드만 추가하면 된다.** SQL은 JPA가 처리하기 때문에 손댈 것이 없다.

- 패러다임의 불일치 해결
  - [JPA와 상속](#jpa와-상속)
  - [JPA와 연관관계, 객체 그래프 탐색](#jpa와-연관관계-및-객체-그래프-탐색)
  - [JPA와 비교하기](#jpa와-비교하기)

# JPA와 상속

## JPA와 상속 - 저장

- **개발자가 할 일**

  ```
  jpa.persist(album);
  ```

- **나머진 JPA가 처리**
  ```
  INSERT INTO ITEM ...
  INSERT INTO ALBUM ...
  ```

### JPA와 상속 - 조회

- **개발자가 할 일**

  ```
  Album album = jpa.find(Album.class, albumId);
  ```

- **나머진 JPA가 처리**

  ```
  SELECT I.*, A.*
  FROM Item I
  JOIN ALBUM A ON I.ITEM_ID = A.ITEM_ID
  ```

- 성능
- 데이터 접근 추상화와 벤더 독립성
- 표준

---

## JPA와 연관관계 및 객체 그래프 탐색

- **연관관계 저장**

  ```
  member.setTeam(team);
  jpa.persist(member);
  ```

- **객체 그래프 탐색**

  ```
  Member member = jpa.find(Member.class, memberId);
  Team team = member.getTeam();
  ```

---

## JPA와 비교하기

---

- 성능
- 데이터 접근 추상화와 벤더 독립성
- 표준

---

# 참고

- [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/lecture/21683?tab=curriculum&volume=1.00&quality=auto)
