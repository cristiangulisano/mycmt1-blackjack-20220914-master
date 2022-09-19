package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Hand {

    private final List<Card> hand = new ArrayList<>();

    public Hand(List<Card> cards) {
        hand.addAll(cards);
    }
    public Hand(){

    }

    void addCard(Card draw){
        hand.add(draw);
    }

    public int value() {
        int handValue = hand
                .stream()
                .mapToInt(Card::rankValue)
                .sum();

        // does the hand contain at least 1 Ace?
        boolean hasAce = hand
                .stream()
                .anyMatch(card -> card.rankValue() == 1);

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (hasAce && handValue <= 11) {
            handValue += 10;
        }

        return handValue;
    }

    void displayHand() {
        System.out.println(hand.stream()
                               .map(Card::display)
                               .collect(Collectors.joining(
                                       ansi().cursorUp(6).cursorRight(1).toString())));
    }

    Card faceUpCard() {
        return hand.get(0);
    }

    boolean isBusted() {
        return value() > 21;
    }

    void displayHandValue() {
        System.out.println(" (" + value() + ")");
    }

    boolean shouldDealeHit() {
        return value() <= 16;
    }

    boolean pushes(Hand hand) {
        return value() == hand.value();
    }

    boolean beats(Hand hand) {
        return value() > hand.value();
    }
}
