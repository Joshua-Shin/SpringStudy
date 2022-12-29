//package hello.core.xml;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import hello.core.member.MemberService;
//
//import org.assertj.core.api.Assertions;
//import org.assertj.core.api.Assertions.*;
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.jupiter.api.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//
//public class XmlAppContext {
//    @Test
//    void xmlAppContext() {
//        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
//        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        assertThat(memberService).isInstanceOf(MemberService.class);
//    }
//}


package hello.core.xml;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import static org.assertj.core.api.Assertions.*;
public class XmlAppContext {
    @Test
    void xmlAppContext() {
        ApplicationContext ac = new
                GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService",
                MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}