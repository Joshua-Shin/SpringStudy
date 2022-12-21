package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{
    private int discountRateAmount = 10;
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP) {
            return price * discountRateAmount / 100;
        }
        return 0;
    }
}
