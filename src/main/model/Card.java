package model;

import java.util.Random;

public class Card {
    private int num; // 3-14, Ace is 14, J,Q,K is 11, 12, 13 respectively
    private String suit;
//    boolean isJoker;
//    boolean isTwo;

    public Card() {
        Random random = new Random();
        num = random.nextInt(12) + 3;
        int suitInt = random.nextInt(4) + 1;
        if (suitInt == 1) {
            suit = "spades";
        } else if (suitInt == 2) {
            suit = "clubs";
        } else if (suitInt == 3) {
            suit = "hearts";
        } else {
            suit = "diamonds";
        }
    }

    public String getNum() {
        return Integer.toString(num);
    }

    public int getIntNum() {
        return num;
    }

    public String getSuit() {
        return suit;
    }
}
