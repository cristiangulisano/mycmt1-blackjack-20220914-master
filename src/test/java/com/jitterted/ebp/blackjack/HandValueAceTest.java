package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HandValueAceTest {


    @Test
    public void handWithOneAceTwoCardsIsValuedAt11() throws Exception {
        Hand hand = HandFactory.createHandWithRankOf("A", "5");

        assertThat(hand.value())
                .isEqualTo(11 + 5);
    }

    @Test
    public void handWithOneAceAndOtherCardsEqualTo11IsValuedAt11() throws Exception {
        Hand hand = HandFactory.createHandWithRankOf("A", "8", "3");

        assertThat(hand.value())
                .isEqualTo(1 + 8 + 3);
    }

    @Test
    public void handWithOneAceAndJackEqualTo21() throws Exception {
        Hand hand = HandFactory.createHandWithRankOf("A", "J");

        assertThat(hand.value())
                .isEqualTo(11 + 10);
    }

    @Test
    public void handWithOneAceAndJackAndTwoEqualTo13() throws Exception {
        Hand hand = HandFactory.createHandWithRankOf("A", "J", "2");

        assertThat(hand.value())
                .isEqualTo(1 + 10 + 2);
    }
}
