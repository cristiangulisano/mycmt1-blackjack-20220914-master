package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final List<Card> dealerHand = new ArrayList<>();
    private final List<Card> playerHand = new ArrayList<>();

    public static void main(String[] args) {
        initializeScreen();

        dealAndPlay();

        System.out.println(ansi().reset());
    }

    private static void dealAndPlay() {
        Game game = new Game();
        System.console().readLine();
        game.initialDeal();
        game.play();
    }

    private static void initializeScreen() {
        AnsiConsole.systemInstall();
        wellcomeMsg();
        waitForUser();
    }

    private static void waitForUser() {
        System.out.println(ansi()
                           .cursor(3, 1)
                           .fgBrightBlack().a("Hit [ENTER] to start..."));
    }

    private static void wellcomeMsg() {
        System.out.println(ansi()
                            .bgBright(Ansi.Color.WHITE)
                            .eraseScreen()
                            .cursor(1, 1)
                            .fgGreen().a("Welcome to")
                            .fgRed().a(" JitterTed's")
                            .fgBlack().a(" BlackJack game"));
    }

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {

        // deal first round of cards, players first
        dealPlayerAndDealer();

        // deal next round of cards
        dealPlayerAndDealer();
    }

    private void dealPlayerAndDealer() {
        playerHand.add(deck.draw());
        dealerHand.add(deck.draw());
    }

    public void play() {
        boolean playerBusted = false;
        playerBusted = playerPlays(playerBusted);
        dealerPlays(playerBusted);
        gameResult(playerBusted);
    }

    private boolean playerPlays(boolean playerBusted) {
        // get Player's decision: hit until they stand, then they're done (or they go bust)
        while (!playerBusted) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerStands(playerChoice)) {
                break;
            }
            if (playerHits(playerChoice)) {
                playerBusted = playerHitResult(playerBusted);
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
        return playerBusted;
    }

    private boolean playerHitResult(boolean playerBusted) {
        playerHand.add(deck.draw());
        if (handValueOf(playerHand) > 21) {
            playerBusted = true;
        }
        return playerBusted;
    }

    private static boolean playerHits(String playerChoice) {
        return playerChoice.startsWith("h");
    }

    private static boolean playerStands(String playerChoice) {
        return playerChoice.startsWith("s");
    }

    private void gameResult(boolean playerBusted) {
        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (handValueOf(dealerHand) > 21) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (handValueOf(dealerHand) < handValueOf(playerHand)) {
            System.out.println("You beat the Dealer! 💵");
        } else if (handValueOf(dealerHand) == handValueOf(playerHand)) {
            System.out.println("Push: You tie with the Dealer. 💸");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }

    private void dealerPlays(boolean playerBusted) {
        // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
        if (!playerBusted) {
            while (handValueOf(dealerHand) <= 16) {
                dealerHand.add(deck.draw());
            }
        }

        displayFinalGameState();
    }

    public int handValueOf(List<Card> hand) {
        int handValue = hand
                .stream()
                .mapToInt(Card::rankValue)
                .sum();

        // does the hand contain at least 1 Ace?
        boolean hasAce = hand
                .stream()
                .anyMatch(card -> card.rankValue() == 1);

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (hasAce && handValue < 11) {
            handValue += 10;
        }

        return handValue;
    }

    private String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(dealerHand.get(0).display()); // first card is Face Up

        // second card is the hole card, which is hidden
        displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        displayHand(playerHand);
        System.out.println(" (" + handValueOf(playerHand) + ")");
    }

    private void displayBackOfCard() {
        System.out.print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }

    private void displayHand(List<Card> hand) {
        System.out.println(hand.stream()
                               .map(Card::display)
                               .collect(Collectors.joining(
                                       ansi().cursorUp(6).cursorRight(1).toString())));
    }

    private void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        displayHand(dealerHand);
        System.out.println(" (" + handValueOf(dealerHand) + ")");

        System.out.println();
        System.out.println("Player has: ");
        displayHand(playerHand);
        System.out.println(" (" + handValueOf(playerHand) + ")");
    }
}
