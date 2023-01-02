package hello.core.discount;

import hello.core.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountRateAmount = 10;
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP) {
            return price * discountRateAmount / 100;
        }
        return 0;
    }
}
