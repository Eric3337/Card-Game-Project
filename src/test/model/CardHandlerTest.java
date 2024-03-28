package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardHandlerTest {
    private CardHandler testCardHandler;
    private List<Card> testPlayerCards;
    private List<Card> testCompCards;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void runBefore() {
        testCardHandler = new CardHandler();
        testPlayerCards = new ArrayList<>();
        testCompCards = new ArrayList<>();
        card1 = new Card(1, "spades");
        card2 = new Card(2, "diamonds");
        card3 = new Card(3, "clubs");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCardHandler.getPlayerCards().size());
        assertEquals(0, testCardHandler.getCompCards().size());
        assertEquals(0, testCardHandler.getSortedCardsMap().size());
    }

    @Test
    void testCompLowestCardToPlay() {
        testCompCards.add(card3);
        testCompCards.add(card1);
        testCompCards.add(card2);
        assertEquals(card1, testCardHandler.compLowestCardToPlay(testCompCards));
    }

    @Test
    void testDealCardsEmptyDecks() {
        testCardHandler.dealCards(3, testPlayerCards, testCompCards);
        assertEquals(3, testCardHandler.getPlayerCards().size());
        assertEquals(3, testCardHandler.getCompCards().size());
    }

    @Test
    void testSortCardsForPlayer() {
        testPlayerCards.add(card3);
        testPlayerCards.add(card1);
        testPlayerCards.add(card2);
        testCardHandler.sortCardsForPlayer(testPlayerCards);
        assertEquals(3, testCardHandler.getSortedCardsMap().size());
    }

    @Test
    void testIsRandCardInHand() {
        assertFalse(testCardHandler.isRandCardInHand(testPlayerCards, card1));
        testPlayerCards.add(card1);
        assertTrue(testCardHandler.isRandCardInHand(testPlayerCards, card1));
    }

    @Test
    void testCheckValidNumOfCardsWithNoInitialCardsInDecks() {
        testCardHandler.checkValidNumOfCards(3, testPlayerCards, testCompCards);
        assertEquals(3, testCardHandler.getPlayerCards().size());
        assertEquals(3, testCardHandler.getCompCards().size());
    }

    @Test
    void testCheckValidNumOfCardsWithSomeInitialCardsInDecks() {
        testPlayerCards.add(card1);
        testPlayerCards.add(card1);
        testCompCards.add(card2);
        testCardHandler.checkValidNumOfCards(3, testPlayerCards, testCompCards);
        assertEquals(3, testCardHandler.getPlayerCards().size());
        assertEquals(3, testCardHandler.getCompCards().size());
    }
}
