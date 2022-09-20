package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;
    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private int playerBalance = 0;
    private int playerCurrentBet = 0;

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
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
    }

    public void play() {
        boolean playerBusted = false;
        playerBusted = playerPlays(playerBusted);
        dealerPlays(playerBusted);
        displayGameState();
        GameOutcome gameOutcome = determineOutcome(playerBusted);
        playerBalance += gameOutcome.payoffAmount(playerCurrentBet);
    }

    private boolean playerPlays(boolean playerBusted) {
        // get Player's decision: hit until they stand, then they're done (or they go bust)
        while (!playerBusted) {
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerStands(playerChoice)) {
                break;
            }
            if (playerHits(playerChoice)) {
                playerBusted = playerHitResult();
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
        return playerBusted;
    }

    private boolean playerHitResult() {
        playerHand.addCard(deck.draw());
        return playerHand.isBusted();
    }

    private static boolean playerHits(String playerChoice) {
        return playerChoice.startsWith("h");
    }

    private static boolean playerStands(String playerChoice) {
        return playerChoice.startsWith("s");
    }

    private GameOutcome determineOutcome(boolean playerBusted) {
        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
            return GameOutcome.PLAYER_LOSES;
        } else if (dealerHand.isBusted()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
            return GameOutcome.PLAYER_WINS;
        } else if (playerHand.beats(dealerHand)) {
            System.out.println("You beat the Dealer! 💵");
            return GameOutcome.PLAYER_WINS;
        } else if (dealerHand.pushes(playerHand)) {
            System.out.println("Push: You tie with the Dealer. 💸");
            return GameOutcome.PLAYER_PUSHES;
        } else {
            System.out.println("You lost to the Dealer. 💸");
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerPlays(boolean playerBusted) {
        // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
        if (!playerBusted) {
            while (dealerHand.shouldDealeHit()) {
                dealerHand.addCard(deck.draw());;
            }
        }
        displayFinalGameState();
    }


    private String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(dealerHand.faceUpCard().display()); // first card is Face Up

        // second card is the hole card, which is hidden
        displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        playerHand.displayHand();
        dealerHand.displayHandValue();
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

    private void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        dealerHand.displayHand();
        dealerHand.displayHandValue();

        System.out.println();
        System.out.println("Player has: ");
        playerHand.displayHand();
        dealerHand.displayHandValue();
    }

    public int playerBalance(){
        return playerBalance;
    }

    public void playerDeposits(int amount) {
        playerBalance += amount;
    }

    public void playerBets(int amount) {
        playerCurrentBet = amount;
        playerBalance -= amount;
    }

    public void playerWins() {
        playerBalance += playerBalance * 2;
    }

    public void playerLoses() {
    }

    public void playerPushes() {
        playerBalance += playerCurrentBet;
        playerCurrentBet = 0;
    }

    public void playerWinsWithBJ() {
        playerBalance += playerBalance * 2.5;
    }
}
