package com.jitterted.ebp.blackjack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HandFactory {

    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    static Hand createHandWithRankOf(String... ranks) {
        List<Card> cards = Arrays.stream(ranks).map(rank -> new Card(DUMMY_SUIT, rank))
                                 .collect(Collectors.toList());
        return new Hand(cards);
    }
}
