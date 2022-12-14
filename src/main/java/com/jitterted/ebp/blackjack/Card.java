package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class Card {
    private final Suit suit;
    private final String rank;

    public Card(Suit suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int rankValue() {
        if (isJackQueenKing()) {
            return 10;
        } else if (isAce()) {
            return 1;
        } else {
            return Integer.parseInt(rank);
        }
    }

    private boolean isAce() {
        return rank.equals("A");
    }

    private boolean isJackQueenKing() {
        return "JQK".contains(rank);
    }

    public String display() {
        String[] lines = new String[7];
        lines[0] = "┌─────────┐";
        lines[1] = String.format("│%s%s       │", rank, gapForRankSize());
        lines[2] = "│         │";
        lines[3] = String.format("│    %s    │", suit.symbol());
        lines[4] = "│         │";
        lines[5] = String.format("│       %s%s│", gapForRankSize(), rank);
        lines[6] = "└─────────┘";

        return cardRepresentation(lines, suit);
    }

    private static String cardRepresentation(String[] lines, Suit suit) {
        Ansi.Color cardColor = suit.isRed()
                ? Ansi.Color.RED
                : Ansi.Color.BLACK;
        return ansi()
                .fg(cardColor).toString()
                + String.join(ansi().cursorDown(1)
                                    .cursorLeft(11)
                                    .toString(), lines);
    }

    private String gapForRankSize() {
        return rank.equals("10") ? "" : " ";
    }

    @Override
    public String toString() {
        return "Card {" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!suit.equals(card.suit)) return false;
        return rank.equals(card.rank);
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}
