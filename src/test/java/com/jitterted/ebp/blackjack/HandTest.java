package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HandTest {

    @Test
    public void handWithCardsValueAt22IsBusted() throws Exception {
        Hand hand = HandFactory.createHandWithRankOf("8", "8", "6");

        assertThat(hand.isBusted())
                .isTrue();
    }

    @Test
    public void handWithCardsValueAt21IsNotBusted() throws Exception {
        Hand hand = HandFactory.createHandWithRankOf("8", "8", "5");

        assertThat(hand.isBusted())
                .isFalse();
    }
}
