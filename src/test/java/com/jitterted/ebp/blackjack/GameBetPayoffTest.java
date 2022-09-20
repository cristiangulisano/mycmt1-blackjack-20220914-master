package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameBetPayoffTest {

    @Test
    void newGamePlayerBalanceIsZero() {
        Game game = new Game();

        assertThat(game.playerBalance())
                .isZero();
    }

    @Test
    void playerWithZeroBalanceDeposits50() {
        Game game = newGameWithBalanceOf(50);

        assertThat(game.playerBalance())
                .isEqualTo(50);
    }

    @Test
    void playerWith100Bets50ThenBalanceIs50() {
        Game game = newGameWithBalanceOf(100);

        game.playerBets(50);

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50);
    }

    @Test
    void playerWith100Bets50AndWinsThenBalanceIs150() {
        Game game = newGameWithBalanceOf(100);
        game.playerBets(50);

        game.playerWins();

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50 + (50 * 2));
    }

    @Test
    void playerWith100Bets50AndLosesThenBalanceIs50() {
        Game game = newGameWithBalanceOf(100);
        game.playerBets(50);

        game.playerLoses();

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50);
    }

    @Test
    void playerWith100Bets50AndPushesThenBalanceIs100() {
        Game game = newGameWithBalanceOf(100);
        game.playerBets(50);

        game.playerPushes();

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50 + 50);
    }

    @Test
    void playerWith100Bets50AndWinsWithBJThenBalanceIs175() {
        Game game = newGameWithBalanceOf(100);
        game.playerBets(50);

        game.playerWinsWithBJ();

        assertThat(game.playerBalance())
                .isEqualTo((int) (100 - 50 + 50 * 2.5));
    }

    private static Game newGameWithBalanceOf(int amount) {
        Game game = new Game();
        game.playerDeposits(amount);
        return game;
    }
}
