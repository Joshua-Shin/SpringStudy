# 스프링 핵심 원리 - 기본편
-------------
### 단원별 요점 정리

#### 객체 지향 설계와 스프링
- 스프링은 좋은 객체 지향 프로그래밍을 위한 것.
- 객체 지향의 꽃은 '다형성' + SOLID
- 다형성
  - 어떤 객체의 속성이나 기능이 상황에 따라 여러 가지 형태를 가질 수 있는 성질
  - 역할과 구현으로 구분해라.
  - 자동자가 역할. K5, 그랜저, 테슬라가 구현.
  - 운전자는 구현체에 의존하는것이 아니라 역할에 의존하기에, K5에서 그랜저로 차가 바뀌어도 운전을 할 수 있어.
  - 역할은 인터페이스로, 구현은 구현체로 개발.


#### 스프링 핵심 원리 이해1 - 예제 만들기
- 회원 도메인 설계 <br> <img width="800" alt="스크린샷 2023-04-12 오후 2 03 29" src="https://user-images.githubusercontent.com/93418349/231354927-75869b32-5272-4f3b-bf84-9badfa09dcc9.png">
- 주문과 할인 도메인 설계 <br> <img width="800" alt="스크린샷 2023-04-12 오후 2 03 45" src="https://user-images.githubusercontent.com/93418349/231354933-93a3a768-3229-408d-80bc-dc5067ff5c5f.png">
- enum 타입은 == 으로 비교하는게 맞음


#### 스프링 핵심 원리 이해2 - 객체 지향 원리 적용
- 이전 코드의 문제점
  - OrderServiceImpl의 필드 private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); 
  - 이는 인터페이스인 discountPolicy와 구현체인 FixDiscountPolicy 둘다에 의존을 하고 있는거. => DIP 지켜지지 않음
  - 할인 정책을 FixDiscountPolicy에서 RateDiscountPolicy로 바꾸려면 해당 필드를 없애고 새로 선언해야함. => OCP 지켜지지 않음.
- 관심사의 분리
  - 배우는 상대역을 직접 캐스팅하지 않는다.
  - 캐스팅 하는 역할은 배우가 아니라 공연기획자가 하는일이다.
  - AppConfig 라는, 구현 객체를 생성하고 주입하는 역할을 맡는 설정 클래스를 만드는거야. 일종의 공연 기획자 역할.
  - 때문에 어떤 객체를 의존할지 그러한 구성 역할을 실행 역할을 해오던 ServiceImpl 에게서 분리시킬 수 있음.
  - 주입은 AppConfig가 해주니까, OrderServiceImpl는 필드로 private final DiscountPolicy discountPolicy; 을 가지게 되어 인터페이스만 의존하게 되게됨 => DIP 지킴
  - 할인 정책을 FixDiscountPolicy에서 RateDiscountPolicy로 바꾸려면 실행영역인 OrderServiceImpl가 아니라 구성영역인 AppConfig에서만 수정하며 됨 => OCP 지킴
  - 관심사를 분리함으로서 SRP도 지키게됨.
- 기타사항
  - final 선언된건 무조건 기본으로 할당을 하든, 생성자로 할당을 하든 해야됨.
  - 현재 orderServiceImpl이 참조하는 memberRespository랑 memberServiceImpl이 참조하는 memberRespository랑 다른데, memberRespository에 저장한 member가 동일하게 불려지는 이유
    - memberRespository이 store가 static으로 선언되어있음!!
- 제어의 역전 IoC (Inversion of Control)
  -  프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것. 
  -  AppConfig가 객체 생성해주고 주입해주고 함으로서 제어권을 외부로 둔거.
- 프레임워크 vs 라이브러리
  - 프레임워크가 내가 작성한 코드를 제어하고, 대신 실행하면 그것은 프레임워크가 맞다. (JUnit)
    - JUnit도 그냥 나는 테스트 로직만 구현한건데 얘가 본인의 로직대로 실행하고 결과를 내놓으니 프레임워크
  - 반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면 그것은 프레임워크가 아니라 라이브러리다.
- DI(Dependency Injection)
  - 의존관계를 외부에서 주입해주는것 같다고 해서 의존관계 주입이라 함.
  - extensd와 implements 이런것도 결국 의존하고 있다는거네.
  - 의존관계를 정적인 클래스 의존관계와 실행 시점에 결정되는 동적인 객체(인스턴스) 의존관계로 나눌 수 있어.
    - 정적인 클래스 의존관계 : 코드만 봐도 어떤 객체가 현재 어떤 객체들을 참조하고 있는지 보이지.
    - 동적인 객체 의존관계 : 애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계.
  - AppConfig 처럼 객체 생성, 관리, 주입 해주는 애를 DI 컨테이너라 한다.
- @Configuration
  - 구성 역할을 하던 AppConfig 클래스에다가 @Configuration 선언.
  - 객체를 생성하고 주입하던 메소드에 @Bean 이라 선언
  - 하면, 스프링이 해당 메소드들을 모두 호출해서 반환된 객체를 Spring 컨테이너(DI 컨테이너 기능을 하는 Spring 전용 컨테이너)에 등록함.
  - 이렇게 등록된 객체를 **스프링 빈** 이라 한다.
  - 메소드 이름이 key가 되고 등록된 객체가 value가 됨.
  - 빈 불러오는 법
    - ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    - MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
  -  ApplicationContext 를 스프링 컨테이너라 한다.


#### 스프링 컨테이너와 스프링 빈
#### 싱글톤 컨테이너
#### 컴포넌트 스캔
#### 의존관계 자동 주입
#### 빈 생명주기 콜백
#### 빈 스코프
