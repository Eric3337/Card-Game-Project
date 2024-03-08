package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Random;

// Card is representing a card that has a number and a suit
//      the number is ranging from 3 to 14, where 11 is J, 12 is Q, 13 is K,
//      and 14 is ace.
public class Card implements Writable {
    private int num;
    private String suit;

    // MODIFIES: this
    // EFFECTS: creates a card with a random number and a random suit
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

    public Card(int num, String suit) {
        this.num = num;
        this.suit = suit;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("int", num);
        json.put("suit", suit);
        return json;
    }
}
