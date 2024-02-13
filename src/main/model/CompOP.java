package model;

import java.util.ArrayList;

public class CompOP {
    private ArrayList<Card> cardsInHand;
    private int opLevel;
    private Card selectedCard;

    // EFFECTS: creates a computer opponent with no cards in hand and starts at level 3
    public CompOP() {
    }

    // MODIFIES: this
    // EFFECTS: assigns given card as the selected card
    public void selectCard(Card card) {
    }

    // EFFECTS: plays the card down in the middle
    public void playCardDown() {
    }

    public Card getSelectedCard() {
        return null;
    }

    public int getNumCardsInHand() {
        return 0;
    }

    public int getOpLevel() {
        return 0;
    }
}
