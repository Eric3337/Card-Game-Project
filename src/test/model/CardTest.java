package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private Card card;

    @BeforeEach
    void runBefore() {
        card = new Card();
    }

    @Test
    void testCard() {
        List<String> testSuitList = new ArrayList<>();
        testSuitList.add("spades");
        testSuitList.add("clubs");
        testSuitList.add("hearts");
        testSuitList.add("diamonds");
        assertTrue(testSuitList.contains(card.getSuit()));

        List<Integer> testNumList = new ArrayList<>();
        testNumList.add(3);
        testNumList.add(4);
        testNumList.add(5);
        testNumList.add(6);
        testNumList.add(7);
        testNumList.add(8);
        testNumList.add(9);
        testNumList.add(10);
        testNumList.add(11);
        testNumList.add(12);
        testNumList.add(13);
        testNumList.add(14);
        assertTrue(testNumList.contains(card.getIntNum()));
    }

    @Test
    void testGetNum() {
        List<String> testNumList = new ArrayList<>();
        testNumList.add("3");
        testNumList.add("4");
        testNumList.add("5");
        testNumList.add("6");
        testNumList.add("7");
        testNumList.add("8");
        testNumList.add("9");
        testNumList.add("10");
        testNumList.add("11");
        testNumList.add("12");
        testNumList.add("13");
        testNumList.add("14");
        assertTrue(testNumList.contains(card.getNum()));
    }
}
