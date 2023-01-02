package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.OrderService;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class AllBeanTest {
    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        DiscountService discountService = ac.getBean(DiscountService.class);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        Assertions.assertThat(discountPrice).isEqualTo(1000);
        int discountPrice2 = discountService.discount(member, 20000, "rateDiscountPolicy");
        Assertions.assertThat(discountPrice2).isEqualTo(2000);
    }

    // @Component
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;
        DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }
        int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }

    }
}
