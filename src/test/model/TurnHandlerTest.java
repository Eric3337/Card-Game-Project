package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TurnHandlerTest {
    private TurnHandler testTurnHandler;
    private CardHandler testCardHandler;
    private List<Card> testPlayerCards;
    private List<Card> testCompCards;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void runBefore() {
        testTurnHandler = new TurnHandler();
        testPlayerCards = new ArrayList<>();
        testCompCards = new ArrayList<>();
        card1 = new Card(1, "spades");
        card2 = new Card(2, "diamonds");
        card3 = new Card(3, "clubs");
    }

    @Test
    void testDoPlayerAndCompTurn() {
        testPlayerCards.add(card1);
        testPlayerCards.add(card2);
        testCompCards.add(card2);
        testCompCards.add(card3);
        assertEquals(card2, testTurnHandler.doPlayerAndCompTurn(testPlayerCards,
                testCompCards, "1", "spades", null));
        assertEquals(1, testPlayerCards.size());
        assertEquals(1, testCompCards.size());
        testPlayerCards.add(card1);
        testCompCards.add(card2);

        assertEquals(card3, testTurnHandler.doPlayerAndCompTurn(testPlayerCards,
                testCompCards, "2", "diamonds", card1));
        assertEquals(1, testPlayerCards.size());
        assertEquals(1, testCompCards.size());
        testPlayerCards.add(card1);
        testCompCards.add(card3);

        assertEquals(card3, testTurnHandler.doPlayerAndCompTurn(testPlayerCards,
                testCompCards, "2", "diamonds", card3));

        testPlayerCards.clear();
        testCompCards.clear();

        assertEquals(card1, testTurnHandler.doPlayerAndCompTurn(testPlayerCards,
                testCompCards, "2", "diamonds", card1));
    }

    @Test
    void testDoCompTurn() {
        assertNull(testTurnHandler.doCompTurn(testCompCards, null));
        testCompCards.add(card2);
        assertEquals(card2, testTurnHandler.doCompTurn(testCompCards, card1));

        testCompCards.add(card1);
        assertEquals(null, testTurnHandler.doCompTurn(testCompCards, card2));
    }
}