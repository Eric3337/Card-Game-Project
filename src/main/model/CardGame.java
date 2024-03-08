package model;

import ui.CardApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardGame {
    private CardApp cardApp;


    // EFFECTS: initializes a new game that creates a random number of cards for the player
    //          and opponent from 11 to 20, and keeps track of each side's cards.
    //          Then, it deals cards to each player and runs the game accordingly
    public void newGame(Account accountSignedIn) {
        Random random = new Random();
        int numCardsPerPlayer = random.nextInt(11) + 10;

        List<Card> playerCards = new ArrayList<Card>();
        List<Card> compCards = new ArrayList<Card>();

        dealCards(numCardsPerPlayer, playerCards, compCards);

        runGame(playerCards, compCards, accountSignedIn);
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

    // EFFECTS: runs a game of cards based off the rules of the game and which cards the player has
    //          selected to play down
    //          if either the player or opponent (computer opponent) runs out of cards,
    //          print out appropriate message and add a victory or loss to player accordingly
    //          then adds one game to total games played
    private void runGame(List<Card> playerCards, List<Card> compCards, Account accountSignedIn) {
        boolean isGamePlaying = true;
        Card lastCardPlayed = null;

        while (isGamePlaying) {
            cardApp.gameSetUp(playerCards, lastCardPlayed);

            lastCardPlayed = cardApp.executeGameInteraction(playerCards, compCards, lastCardPlayed);

            isGamePlaying = cardApp.gameOver(playerCards, compCards);
        }
        accountSignedIn.playedAGame();
    }

    // EFFECTS checks whether a card is in a hand
    public boolean isRandCardInHand(List<Card> cardsInHand, Card randomCard) {
        for (int j = 0; j < cardsInHand.size(); j++) {
            Card currCard = cardsInHand.get(j);
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
        while (playerCards.size() != numCardsPerPlayer || compCards.size() != numCardsPerPlayer) {
            Card randPCard = new Card();
            Card randCCard = new Card();
            if (playerCards.size() != numCardsPerPlayer) {
                if (!isRandCardInHand(playerCards, randPCard) && !isRandCardInHand(compCards, randPCard)) {
                    playerCards.add(randPCard);
                }
            }

            if (compCards.size() != numCardsPerPlayer) {
                if (!isRandCardInHand(playerCards, randCCard) && !isRandCardInHand(compCards, randCCard)) {
                    compCards.add(randCCard);
                }
            }
        }
    }
}
