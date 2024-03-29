package model;

import ui.MessagePrinter;

import java.util.*;

// Represents a card handler that can choose the lowest card for the computer when player passes,
//          deal cards to both player and computer with no duplicate in both hands,
//          sort and order player cards
public class CardHandler {
    private List<Card> playerCards;
    private List<Card> compCards;
    private MessagePrinter msgPrinter;
    private Map<Integer, Card> sortedCardsMap;

    // EFFECTS: constructs a CardHandler with an empty deck and message printer
    public CardHandler() {
        this.playerCards = new ArrayList<>();
        this.compCards = new ArrayList<>();
        this.sortedCardsMap = new HashMap<>();
        this.msgPrinter = new MessagePrinter();
    }

    // REQUIRES: compCard.size() > 0
    // EFFECTS: returns the computer's lowest number card in its deck
    //          and removes that lowest card from the deck
    public Card compLowestCardToPlay(List<Card> compCards) {
        Card lowestCardNum = compCards.get(0);
        for (Card card : compCards) {
            if (card.getIntNum() < lowestCardNum.getIntNum()) {
                lowestCardNum = card;
            }
        }
        compCards.remove(compCards.get(compCards.indexOf(lowestCardNum)));
        return lowestCardNum;
    }

    // REQUIRES: playerCards.size() <= numCardsPerPlayer, compCards.size() <= numCardsPerPlayer
    // EFFECTS: deals cards to both computer opponent and player ensuring that there are the correct number of
    //          cards for each player and with no duplicates in each deck
    public void dealCards(int numCardsPerPlayer, List<Card> playerCards, List<Card> compCards) {
        for (int i = 0; i < numCardsPerPlayer; i++) {
            Card randPCard = new Card();
            Card randCCard = new Card();

            if (playerCards.size() == 0) {
                playerCards.add(randPCard);
                compCards.add(randCCard);
            }

            if (!isRandCardInHand(playerCards, randPCard) && !isRandCardInHand(compCards, randPCard)) {
                playerCards.add(randPCard);
            }
            if (!isRandCardInHand(playerCards, randCCard) && !isRandCardInHand(compCards, randCCard)) {
                compCards.add(randCCard);
            }
        }
        checkValidNumOfCards(numCardsPerPlayer, playerCards, compCards);
        this.playerCards = playerCards;
        this.compCards = compCards;
    }

    // EFFECTS: sorts the player's cards in increasing card number order,
    //          also prints out "cardNum of cardSuit" for each card in player's deck
    public void sortCardsForPlayer(List<Card> playerCards) {
        Map<Integer, Card> sortedCards = new HashMap<>();
        for (Card card : playerCards) {
            int cardNum = card.getIntNum();
            sortedCards.put(cardNum, card);
        }
        this.sortedCardsMap = sortedCards;

        for (Integer cardNum : sortedCards.keySet()) {
            msgPrinter.printMessage(cardNum + " of " + sortedCards.get(cardNum).getSuit());
        }
    }

    // EFFECTS checks whether a card is in a hand
    public boolean isRandCardInHand(List<Card> cardsInHand, Card randomCard) {
        for (Card currCard : cardsInHand) {
            int currentCardNum = currCard.getIntNum();
            String currentCardSuit = currCard.getSuit();
            if (randomCard.getIntNum() == currentCardNum && randomCard.getSuit().equals(currentCardSuit)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: checks whether there are the correct number of cards in each hand
    public void checkValidNumOfCards(int numCardsPerPlayer, List<Card> playerCards, List<Card> compCards) {
        while (playerCards.size() < numCardsPerPlayer || compCards.size() < numCardsPerPlayer) {
            Card randPCard = new Card();
            Card randCCard = new Card();
            if (playerCards.size() < numCardsPerPlayer) {
                if (!isRandCardInHand(playerCards, randPCard) && !isRandCardInHand(compCards, randPCard)) {
                    playerCards.add(randPCard);
                }
            }

            if (compCards.size() < numCardsPerPlayer) {
                if (!isRandCardInHand(playerCards, randCCard) && !isRandCardInHand(compCards, randCCard)) {
                    compCards.add(randCCard);
                }
            }
        }
        this.playerCards = playerCards;
        this.compCards = compCards;
    }

    public List<Card> getPlayerCards() {
        return this.playerCards;
    }

    public List<Card> getCompCards() {
        return this.compCards;
    }

    public Map<Integer, Card> getSortedCardsMap() {
        return sortedCardsMap;
    }
}
