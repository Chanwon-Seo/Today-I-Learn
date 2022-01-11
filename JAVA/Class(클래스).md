# Class(클래스)

Car.java

```java
class Tire{

}
public class Car{
    // 필드 : 객체의 데이터를 저장
    int speed;
    // 생성자 : 객체 생성 시 초기화 역할 담당
    public Car() {

    }

    // 메서드 : 객체의 동작에 해당하는 실행 블록
    public int maxSpeed(int speed){
        if(speed > 0){
            speed = 60;
        }else{
            return speed;
        }
    }
}


```

# 참고

- [이것이 자바다](http://www.hanbit.co.kr/store/education/edu_view.html?p_code=C5815590736)
