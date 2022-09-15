package com.jitterted.ebp.blackjack;

public enum Suit {
    HEARTS("♥", "RED"),
    CLUBS("♣", "BLACK"),
    SPADES("♠", "BLACK"),
    DIAMONDS("♦", "RED")
    ;

    private String symbol;
    private String color;

    Suit(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String symbol() {
        return symbol;
    }

    public boolean isRed() {
        return color.equals("RED");
    }
}
