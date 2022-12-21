package hello.core.discount;

import static org.junit.jupiter.api.Assertions.*;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RateDiscountPolicyTest {
    DiscountPolicy discountPolicy = new RateDiscountPolicy();
    @Test
    @DisplayName("VIP 회원이라면")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }
    @Test
    @DisplayName("VIP 회원이 아니라면")
    void vip_x() {
        //given
        Member member = new Member(1L, "memberA", Grade.BASIC);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        Assertions.assertThat(discount).isEqualTo(0);
    }
}