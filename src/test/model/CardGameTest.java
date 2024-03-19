package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CardApp;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class CardGameTest {
    private CardGame testCardGame;
    private Account testAccountSignedIn;
    private Card card1;
    private Card card2;
    private Card card3;


    @BeforeEach
    void runBefore() {
        testAccountSignedIn = new Account("User", "123");
        testCardGame = new CardGame(testAccountSignedIn, null);
        card1 = new Card();
        card2 = new Card();
        card3 = new Card();
    }

    @Test
    void testConstructor() {
        assertEquals(testAccountSignedIn, testCardGame.getAccountSignedIn());
        assertNull(testCardGame.getCardApp());
        assertEquals(0, testCardGame.getPlayerCards().size());
        assertEquals(0, testCardGame.getCompCards().size());
    }

    @Test
    void testAddPlayerCardsOnce() {
        testCardGame.addPlayerCard(card1);
        assertEquals(1, testCardGame.getPlayerCards().size());
        assertEquals(card1, testCardGame.getPlayerCards().get(0));
    }

    @Test
    void testAddPlayerCardsMultiple() {
        testCardGame.addPlayerCard(card1);
        testCardGame.addPlayerCard(card2);
        testCardGame.addPlayerCard(card3);
        assertEquals(3, testCardGame.getPlayerCards().size());
        assertEquals(card1, testCardGame.getPlayerCards().get(0));
        assertEquals(card2, testCardGame.getPlayerCards().get(1));
        assertEquals(card3, testCardGame.getPlayerCards().get(2));
    }

    @Test
    void testAddCompCardsOnce() {
        testCardGame.addCompCard(card1);
        assertEquals(1, testCardGame.getCompCards().size());
        assertEquals(card1, testCardGame.getCompCards().get(0));
    }

    @Test
    void testAddCompCardsMultiple() {
        testCardGame.addCompCard(card1);
        testCardGame.addCompCard(card2);
        testCardGame.addCompCard(card3);
        assertEquals(3, testCardGame.getCompCards().size());
        assertEquals(card1, testCardGame.getCompCards().get(0));
        assertEquals(card2, testCardGame.getCompCards().get(1));
        assertEquals(card3, testCardGame.getCompCards().get(2));
    }
}
