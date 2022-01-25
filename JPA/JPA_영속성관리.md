# JPA 데이터베이스 방언

- JPA는 특정 데이터베이스에 종속하지 않는다.
- 각각의 데이터베이스가 제공하는 SQL 문법과 함수는 조금씩 다르다.
- 방언 : SQL 표준을 지키지 않는 특정 데이터베이스만의 고유한 기능

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello">
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>

            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
            <!--<property name="hibernate.jdbc.batch_size" value="10" />-->
            <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
        </properties>
    </persistence-unit>
  </persistence>
  ```

# JPA 구동 방식

![image](https://user-images.githubusercontent.com/90185805/150949964-79f55eb1-1dab-4a3c-bf9a-e34e99b97bcd.png)

- 표준으로 지정한 META-INF/**persistence.xml**을 설정정보로 참고한 뒤 **EntityManagerFactory**를 생성하고 **EntityManager**를 생성해서 요청을 실행한다.

- 데이터의 값 변경은 transaction안에서 실행되어야 하며 tx.begin이후로 코드를 작성한다.

---

## 실습 객체 및 테이블 생성

```java
@Entity //JPA가 관리할 객체
/**
 * lombok 사용 시
 */
//@Getter
//@Setter
public class Member{

    @Id // 데이터베이스 PK와 매핑
    private Long id;
    private String name;

    //Getter, Setter 생성하기
}
```

```sql
create table Member(
    id bigint not null,
    name varchar(255),
    primary key(id) //Member 객체의 @Id와 매핑된다.
)
```

### 실습을 위해 JpaMain 클래스 생성

```java
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //Persistence.xml의 name

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code..

        try {
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
```

## 주의

- **엔티티 매니저 팩토리**는 하나만 생성해서 애플리케이션 전체에서 공유한다.
- **엔티티 매니저**는 쓰레드간에 공유하면 안된다. (사용하고 버려야 한다.)
- **중요 JPA의 모든 데이터 변경은 트랜잭션 안에서 실행해야한다.**

---

## 실습

### 저장

```java
try{
    //회원 객체 생성
    Member member = new Member();
    member.setId(2L);
    member.setName("HelloB");

    em.persist(member);
    tx.commit();
}
```

- JPA는 em.persist(member); 부분에서 저장되어 INSERT문 쿼리가 날라가는 것이 아닌 tx.commit();에서 쿼리가 날라가는 것을 확인 할 수 있다.

### 조회

```java
try{
    Member findMember = em.find(Member.class, 1L);
    System.out.println("findMember = " + findMember.getId());
    //findMember.id = 1

    System.out.println("findMember = " + findMember.getName());
    //findMember.name = HelloA
}
```

### 수정

```java
/*
 * em.persist()를 이용해 저장하지 않아도 엔티티를 가져올 수 있다.
 */
 try{
    findMember.setName("HelloJPA");

    em.persist(member);
    tx.commit();
 }
```

<hr>

# JPQL

- JPA는 엔티티 객체를 중심으로 개발한다. 하지만 검색 쿼리에서 문제점이 있다.
- 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색한다.
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능하다.
- 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요 함.
- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
- JPQL은 **엔티티 객체를 대상**으로 쿼리
- SQL은 **데이터베이스 테이블을 대상**으로 쿼리
- 테이블이 아닌 **객체를 대상으로 검색하는 객체 지향 쿼리**

# 참고

- [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/lecture/21683?tab=curriculum&volume=1.00&quality=auto)
