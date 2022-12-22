## Spring을 활용한 웹 백엔드 개발 프로젝트
- 본 프로젝트는 <스프링 핵심 원리 :기본편 - 김영한> 강의를 수강하며 진행한 실습 프로젝트 입니다.
- 사용 스킬 : Java(JDK 1.8), Spring framework(2.7.6), JPA, JUnit5, H2 database, IntelliJ(2022.2.3)
-------
### 강의 내용 자유회상
- 스프링은 좋은 객체 지향 프로그래밍을 위한 것.
- 객체 지향의 꽃은 '다형성' + SOLID
- IoC = Inversion of Control 제어의 역전. 실행 영역과 구성 영역을 구분해서 객체를 생성하고 의존관계를 주입하는걸 외부에서 해주는거.
- DI = 의존관계 주입. 생성자 주입으로. 
- AppConfig 클래스를 통해 구성 역할을 분리하지 않았다면, 클래스내에서 참조하고자 하는 객체를 생성하고 주입했음. 이는 추상화도 구체화도 둘다 의존하게 되며 결과적으로 SRP, DIP, OCP을 어기게됨
- 프레임워크 : 내가 작성한 코드를 제어하고 대신 실행해줌. Spring, JUnit..
- DI 컨테이너 : AppConfig 클래스 처럼 객체를 생성하고 의존관계를 주입해주는거.
- 정적인 클래스 다이어그램 : 실행되기 전에 코드상으로 봐도 의존관계를 분석할 수 있는..
- 동적인 클래스 다이어그램 : 실행됐을때, 실제 어떤 클래스가 어떤 객체를 의존하고 있는가 보여주는거.


