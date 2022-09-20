package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletBettingTest {

    @Test
    void bet10fromWalletWith50AndReduceBalanceTo40() {
        var wallet = createWalletWithAmountOf(50);

        wallet.bet(10);

        assertThat(wallet.balance())
                .isEqualTo(50 - 10);
    }

    @Test
    void betNegative10fromWalletWith50AndGetExecption() {
        Wallet wallet = new Wallet();

        assertThatThrownBy(() -> wallet.bet(-10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bet10TwoTimesfromWalletWith50AndReduceBalanceTo30() {
        var wallet = createWalletWithAmountOf(60);

        wallet.bet(10);
        wallet.bet(10);

        assertThat(wallet.balance())
                .isEqualTo(60 - 10 - 10);
    }

    @Test
    void betFullBalanceAndGetIsEmpty() {
        var wallet = createWalletWithAmountOf(50);

        wallet.bet(wallet.balance());

        assertThat(wallet.isEmpty())
                .isTrue();
    }

    @Test
    void betMoreThankFullBalanceAndGetBalanceNotEnough() {
        var wallet = createWalletWithAmountOf(50);

        wallet.bet(wallet.balance());

        assertThatIllegalStateException()
                .isThrownBy(() ->wallet.bet(10))
                .withMessage("Not enough balance");
    }

    private Wallet createWalletWithAmountOf(int amount){
        var wallet = new Wallet();
        wallet.addMoney(amount);
        return wallet;
    }
}
