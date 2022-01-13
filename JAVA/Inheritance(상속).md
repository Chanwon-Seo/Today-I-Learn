# Inheritance(상속)

부모 클래스(상위 클래스)와 자식 클래스(하위 클래스)가 있으며, 자식 클래스는 부모 클래스를 선택하여, **상속**받는다.

## 상속받는 이유

부모 클래스를 재사용해서 만들 수 있기 때문에 효율적이고, 개발 시간을 줄여준다.

## 물려줄 수 없는 부모 클래스

- 부모 클래스의 private 접근 제한을 갖는 필드, 메서드 자식이 물려받을 수 없다.
- 서로 다른 패키지에 있다면, 부모의 default 접근 제한을 갖는 필드, 메서드도 물려 받을 수 없다.

```java
public Class Parent { // 부모 클래스

};

public Class Child extends Parent { // 부모 클래스에서 상속 받은 자식 클래스

};
```

# 참고

- [이것이 자바다](http://www.hanbit.co.kr/store/education/edu_view.html?p_code=C5815590736)
