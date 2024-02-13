package model;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cardsInHand;
    private int playerLevel;
    private Card selectedCard;

    // EFFECTS: creates a player with no cards in hand and starts at level 3
    public Player() {
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

    public int getPlayerLevel() {
        return 0;
    }
}
