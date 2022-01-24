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

1. SQL 중심적인 개발에서 객체 중심으로 개발

2. 생산성

- 저장 : `jpa.persist(member)`
- 조회 : `Member member = jpa.find(memberId)`
- 수정 : `member.setName("변경할 이름")`
- 삭제 : `jpa.remove(member)`

3. 유지보수
4. 데이터 접근 추상화와 벤더 독립성
5. 표준

- 기존: 필드 변경 시 모든 SQL을 수정해야 한다.
- JPA: **필드만 추가하면 된다.** SQL은 JPA가 처리하기 때문에 손댈 것이 없다.

7. 패러다임의 불일치 해결

- [JPA와 상속](#jpa와-상속)
- [JPA와 연관관계, 객체 그래프 탐색](#jpa와-연관관계-및-객체-그래프-탐색)
- [JPA와 비교하기](#jpa와-비교하기)

## 7.1 JPA와 상속

### 7.1.1 JPA와 상속 - 저장

- **개발자가 할 일**

  ```
  jpa.persist(album);
  ```

- **나머진 JPA가 처리**
  ```sql
  INSERT INTO ITEM ...
  INSERT INTO ALBUM ...
  ```

### 7.1.2 JPA와 상속 - 조회

- **개발자가 할 일**

  ```
  Album album = jpa.find(Album.class, albumId);
  ```

- **나머진 JPA가 처리**

  ```sql
  SELECT I.*, A.*
  FROM Item I
  JOIN ALBUM A ON I.ITEM_ID = A.ITEM_ID
  ```

---

## 7.2 JPA와 연관관계 및 객체 그래프 탐색

- 7.2.1 **연관관계 저장**

  ```java
  member.setTeam(team);
  jpa.persist(member);
  ```

- 7.2.2 **객체 그래프 탐색**

  ```java
  Member member = jpa.find(Member.class, memberId);
  Team team = member.getTeam(); // 자유로운 객체 그래프 탐색

  ```

---

## 7.3 JPA와 비교하기

```java
  String memberId = '100';
  Member member1 = new jpa.find(Member.class, memberId);
  Member member2 = new jpa.find(Member.class, memberId);
  member1 == member2; // 같다.
  //동일한 트랜잭션에서 조회한 엔티티는 같음을 보장한다.
```

---

## 8. 성능

- 8.1 1차 캐시와 동일성(identity) 보장

  - 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상

    ```java
    String memberId = "100";
    Member m1 = jpa.find(Member.class, memberId); //SQL
    Member m2 = jpa.find(Member.class, memberId); //캐시
    println(m1 == m2) //true
    //동일한 값일 경우 메모리의 첫번째(m1)를 그대로 반환해준다.
    // SQL 1번만 실행
    ```

- 8.2 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)

  - INSERT

    - 트랜잭션을 커밋할 때까지 INSERT SQL을 모음
    - JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송

    ```java
    transaction.begin(); // [트랜잭션] 시작

    em.persist(memberA);
    em.persist(memberB);
    em.persist(memberC);
    //여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.

    //커밋하는 순간 데이터베이스에 INSERT SQL을 모아서 보낸다.
    transaction.commit(); // [트랜잭션] 커밋
    ```

  - UPDATE

    - UPDATE,DELETE로 인한 로우(DOW)락 시간 초소화
    - 트랜잭션 커밋 시 UPDATE,DELETE SQL 실행하고, 바로 커밋

    ```java
    transaction.begin(); //[트랜잭션] 시작

    changeMember(memberA);
    deleteMember(memberB);
    비즈니스_로직_수행(); //비즈니스 로직 수행 동안 DB 로주 락이 걸리지 않는다.

    //커밋하는 순간 데이터베이스에 UPDATE, DELETE SQL을 보낸다.
    transaction.commit(); // [트랜잭션] 커밋
    ```

- 8.3 지연 로딩과 즉시 로딩

  - 지연 로딩 : **객체가 실제 사용될 때 로딩**

    ```java
    //지연 로딩
    Member member = memberDAO.find(memberId); //SELECT * FROM MEMBER
    Team team = member.getTeam();
    String teamName = team.getName(); //SELECT * FROM TEAM
    ```

  - 즉시 로딩 : JOIN SQL로 **한번에 연관된 객체끼리** 미리 조회

    ```java
    //즉시 로딩
    Member member = memberDAO.find(memberId);
    //SELECT M._, T._ FROM MEMBER JOIN TEAM ...
    Team team = member.getTeam();
    String teamName = team.getName();
    ```

---

# 참고

- [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/lecture/21683?tab=curriculum&volume=1.00&quality=auto)
