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
        Game game = new Game();

        game.playerDeposits(50);

        assertThat(game.playerBalance())
                .isEqualTo(50);
    }

    @Test
    void playerWith100Bets50ThenBalanceIs50() {
        Game game = new Game();
        game.playerDeposits(100);

        game.playerBets(50);

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50);
    }

    @Test
    void playerWith100Bets50AndWinsThenBalanceIs150() {
        Game game = new Game();
        game.playerDeposits(100);
        game.playerBets(50);

        game.playerWins();

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50 + (50 * 2));
    }
}
