package model;

import ui.MessagePrinter;

import java.util.*;

public class CardHandler {
    private List<Card> deck;
    private MessagePrinter msgPrinter;

    public CardHandler() {
        this.deck = new ArrayList<>();
        this.msgPrinter = new MessagePrinter();
    }

    public Card compLowestCardToPlay(List<Card> compCards) {
        Card lowestCardNum = compCards.get(0);
        for (int i = 1; i < compCards.size(); i++) {
            if (compCards.get(i).getIntNum() < lowestCardNum.getIntNum()) {
                lowestCardNum = compCards.get(i);
            }
        }
        compCards.remove(compCards.get(compCards.indexOf(lowestCardNum)));
        return lowestCardNum;
    }

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
    }

    public void sortCardsForPlayer(List<Card> playerCards) {
        Map<Integer, Card> unsortedCards = new HashMap<>();
        orderCards(playerCards, unsortedCards);
    }

    public void orderCards(List<Card> playerCards, Map<Integer, Card> sortedCards) {
        for (Card card : playerCards) {
            int cardNum = card.getIntNum();
            sortedCards.put(cardNum, card);
        }

        for (Integer cardNum : sortedCards.keySet()) {
            msgPrinter.printMessage(cardNum + " of " + sortedCards.get(cardNum).getSuit());
        }
    }

    // EFFECTS checks whether a card is in a hand
    private boolean isRandCardInHand(List<Card> cardsInHand, Card randomCard) {
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
    private void checkValidNumOfCards(int numCardsPerPlayer, List<Card> playerCards, List<Card> compCards) {
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
    }

    public List<Card> getDeck() {
        return this.deck;
    }
}
