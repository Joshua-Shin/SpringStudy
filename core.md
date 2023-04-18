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
    - .getBean(빈이름, 타입)
    - 부모 타입을 조회하면, 자식 타입도 다 조회됨.
  -  ApplicationContext 얘도 인터페이스이며, 얘를 스프링 컨테이너라 한다.


#### 스프링 컨테이너와 스프링 빈
- Test코드 타입 확인
  - Assertions.assertThat(memberService).isInstanceOf(MemberService.class)
- Test코드 same과 equal
  - .isSameAs : 자바코드에서 == 비교
  - .isEqualTo : 자바코드에서 equals 비교

#### 싱글톤 컨테이너
- 순수한 DI 컨테이너에서는 고객의 요청이 올때마다 객체를 새로 생성.
- 매번 객체를 생성하는것은 성능상 너무 안좋아.
- 하나만 생성되고 공유하도록 설계해야돼. 이걸 싱글톤 패턴이라 함.
- 싱글톤 패턴
``` 
public class SingletonService {
    private static final SingletonService instance = new SingletonService();
    public static SingletonService getInstance() {
        return instance;
    }
    private SingletonService() {
    }
}
```
  - 처음 Java가 실행될때만 static으로 객체가 하나만 생성되고 그 이후에는 생성자를 private으로 막았기에 외부에서 생성이 안되게함.
  - 그러나 기본적으로 싱글톤 패턴은 테스트코드 작성도 어렵고 수정도 어렵고 구체화를 참조해야되고 이래저래 유연성이 떨어짐
  - 그러나, 스프링 컨테이너는 이러한 싱글톤 패턴의 단점들을 다 없애주고, 객체를 싱글톤으로 관리.
- 싱글톤 방식의 주의점
  - 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.
  - 무상태(stateless)로 설계해야 한다!
  - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
  - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다!
  - 가급적 읽기만 가능해야 한다.
  - 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
  - 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다!!!
- @Configuration
  - 구성 클래스 AppConfig에다가 @Configuration을 선언해줘야 생성되고 관리되는 빈 객체들이 싱글톤임이 보장돼.
  - AppConfig 가 바이트 조작 되어서 만들어진 AppConfig@CGLIB 가 등록되고, 얘의 @Bean 메소드들은 사실 오버라이드 되는데, 스프링 컨테이너에서 객체가 있으면 찾아서 보내주고 없으면 그때서야 생성하는 로직이 들어가 있음.


#### 컴포넌트 스캔
- 지금까지 설정정보 클래스를 직접 작성해서 @Bean 메소드가 호출되고 객체 생성되고 의존관계 주입되게 만들었는데, 조금 더 나은 방법
- @ConponentScan
  - @ConponentScan 을 설정 클래스에 붙여주면, 해당 클래스가 있는 패키지를 탐색시작위치로 하여, @Component가 붙은 클래스들을 다 찾아서 자동으로 spring bean 등록해줌.
  - @Component 클래스의 생성자에 @Autowired가 붙어있으면 타입이 같은 빈을 찾아서 알아서 자동 의존관계 주입을 해줌. (사실 생성자가 하나라면 @Autowired 생략 가능)
  - 해당 설정 클래스에 @Configuration을 붙이지 않아도 빈들을 싱글톤으로 알아서 잘 관리해주는데, 명시적으로 해당 클래스가 설정 클래스라는것을 보이기 위해 관례상 붙여줌.
  - 다만, 스프링 부트에서 첫 시작인 @SpringBootApplication 안에 사실 @ComponentScan 이 들어있기 때문에, 이런 설정 클래스를 따로 만들지 않아도 됨.
  - 그냥 스프링 빈 등록하고 싶은 클래스들 위에다가 @Component만 붙여도 다 알아서 빈 등록 의존관계주입 싱글톤으로 관리 등 다 해주겠네.
  - 추후에 이렇게 자동등록하는것과 처음에 배웠던 @Configuration 클래스 만들고 @Bean 메소드 만들고 해서 직접 등록하는 수동등록방법의 장/단에 대해 이야기할 예정
- 컴포넌트 스캔 대상과 그의 역할
  - @Component : 컴포넌트 스캔에서 사용. 나머지 대상들 들어가보면 다 @Component가 들어가있음.
  - @Controlller : 스프링 MVC 컨트롤러에서 사용
  - @Service : 스프링 비즈니스 로직에서 사용
  - @Repository : 스프링 데이터 접근 계층에서 사용 
  - @Configuration : 스프링 설정 정보에서 사용
- 애노테이션의 상속 기능?
  - 사실 애노테이션에는 상속관계라는 것이 없다. 그래서 이렇게 애노테이션이 특정 애노테이션을 들고 있는 것을 인식할 수 있는 것은 자바 언어가 지원하는 기능은 아니고, 스프링이 지원하는 기능이다.

#### 의존관계 자동 주입
- 스프링이 @Component 붙어있는 클래스의 @Autowired 붙은 메소드를 찾아서 의존관계를 주입해줌.
- 4가지 방식의 의존 관계 주입법 : 생성자, 수정자(setter), 필드, 메소드 주입
- @Autowired 옵션 
  - @Autowired(required=true) : 기본값, 자동 주입할 대상이 없으면(= 스프링 빈에 등록된 객체가 아닌데 해당 객체 타입이 메소드의 매개변수로 지정되어있다면) 에러 발생
  - @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
  - @Nullable Member member : 자동 주입할 대상이 없으면 null이 입력
  - Optional<Member> member : 자동 주입할 대상이 없으면 Optional.empty 가 입력
- 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용할 수 없다. 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.
- 주입 방법에 대한 결론
  - 생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법이기도 하다.
  - 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다. 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
  - 항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지 않는게 좋다.
  - 생성자가 1개 있을경우 @Autowired 생략 가능하니, 필드들 private final로 선언해주고 롬복으로 @RequiredArgsConstructor 해주는게 가장 안정적.
  - 다만, test코드에서 스프링으로 돌리는게 아니라 순수 자바 코드상에서 단위테스트를 하고 싶을때는 필드주입해서 사용하기도함.
- @Autowired 매칭
  - 1_ 타입 매칭
  - 2_ 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭
- @Qualifier
  - 추가 구분자를 붙여주는 방법. 주입시 추가적인 방법을 제공하는 것이지 빈 이름을 변경하는 것은 아니다.
  - 빈으로 등록할 클래스의 레벨에 @Qualifier("mainDiscountPolicy") 붙여주고
  - 주입시 주입 메소드 매개변수앞에 @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy 이리 붙여주면 됨.
- @Primary
  - 우선순위를 정하는 방법. @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을 가진다.
- @Qualifier vs @Primary
  - 일반적으로 @Primary로 쓰고, 다형성 로직이 필요한 상황에 대응해야할떄 @Qualifier 쓰자.
- 조회한 빈이 모두 필요할때
  - List나 Map 같은걸로 주입 받으면 가능한 타입의 빈들을 다 주입받을 수 있음.
  - 자동 주입할 대상이 없으면 에러가 터지지 않고, 일단 스프링이 빈(empty) Map과 List를 만들고 나서 거기에 주입을 하기에, 그냥 빈 Map, List가 남음.
  - Map<String, DiscountPolicy> 로 받으면 DiscountPolicy 타입의 빈 객체가 쭉 주입되는데, key는 해당 객체 클래스명의 앞글자를 소문자로함.
- 자동 주입 vs 수동 주입
  - 편리한 자동 기능을 기본으로 사용하자
  - 직접 등록하는 기술 지원 객체는 수동 등록 
    - 공통관심사나 기술적인 문제들. 예를 들면 로그인 필터같은거. 이런건 한번에 상황을 보는게 중요하기떄문에 설정 클래스를 따로 둬서 수동주입해주는게 좋아.
  - 다형성을 적극 활용하는 비즈니스 로직은 수동 등록을 고민해보자
    - 이럴때는 FixDiscountPolicy 적용하고 저럴떄는 RateDiscountPolicy를 적용하고 싶은 비즈니스 로직일때도 혹은 더 다양한 정책을 상황에 맞춰 적용하고 싶을떄는, 역시 상황을 한번에 파악할 수 있도록 설정 클래스를 두는게 좋아.
  
#### 빈 생명주기 콜백
#### 빈 스코프
