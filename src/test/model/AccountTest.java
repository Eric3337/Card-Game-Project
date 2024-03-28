package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account account;

    @BeforeEach
    void runBefore() {
        account = new Account("User", "123");
    }

    @Test
    void testAccount() {
        assertEquals("User", account.getUsername());
        assertEquals("123", account.getPw());
        assertEquals(0, account.getGamesWon());
        assertEquals(0, account.getGamesLost());
        assertEquals(0, account.getTotalGamesPlayed());
    }

    @Test
    void testCalculateRatio() {
        account.wonAGame();
        assertEquals(1, account.calculateRatio());
        account.wonAGame();
        assertEquals(2, account.calculateRatio());
        account.lostAGame();
        account.lostAGame();
        assertEquals(1, account.calculateRatio());
    }

    @Test
    void testWonAGameOnce() {
        account.wonAGame();
        assertEquals(1, account.getGamesWon());
    }

    @Test
    void testWonAGameMultiple() {
        account.wonAGame();
        account.wonAGame();
        account.wonAGame();
        assertEquals(3, account.getGamesWon());
    }

    @Test
    void testLostAGameOnce() {
        account.lostAGame();
        assertEquals(1, account.getGamesLost());
    }

    @Test
    void testLostAGameMultiple() {
        account.lostAGame();
        account.lostAGame();
        account.lostAGame();
        assertEquals(3, account.getGamesLost());
    }

    @Test
    void testPlayedAGameOnce() {
        account.playedAGame();
        assertEquals(1, account.getTotalGamesPlayed());
    }

    @Test
    void testPlayedAGameMultiple() {
        account.playedAGame();
        account.playedAGame();
        account.playedAGame();
        assertEquals(3, account.getTotalGamesPlayed());
    }
}