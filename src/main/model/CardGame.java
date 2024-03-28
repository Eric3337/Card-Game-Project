package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import ui.CardApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardGame implements Writable {
    private CardApp cardApp;
    private Account accountSignedIn;
    private List<Card> playerCards;
    private List<Card> compCards;
    private CardHandler cardHandler;
    private static int MIN_NUM_OF_CARDS = 10;
    private static int MAX_NUM_OF_CARDS = 20;

    public CardGame(Account accountSignedIn, CardApp cardApp) {
        this.cardApp = cardApp;
        this.accountSignedIn = accountSignedIn;
        this.playerCards = new ArrayList<>();
        this.compCards = new ArrayList<>();
        this.cardHandler = new CardHandler();
    }

    // EFFECTS: adds given card to player cards
    public void addPlayerCard(Card c) {
        this.playerCards.add(c);
    }

    // EFFECTS: adds given card to computer cards
    public void addCompCard(Card c) {
        this.compCards.add(c);
    }


    // EFFECTS: initializes a new game that creates a random number of cards
    //          (random.nextInt(max - min) + min) for the player
    //          and opponent from 11 to 20, and keeps track of each side's cards.
    //          Then, it deals cards to each player and runs the game accordingly
    public void newGame(Account accountSignedIn) {
        Random random = new Random();
        int numCardsPerPlayer = random.nextInt(MAX_NUM_OF_CARDS - MIN_NUM_OF_CARDS) + MIN_NUM_OF_CARDS;

        cardHandler.dealCards(numCardsPerPlayer, playerCards, compCards);

        runGame(playerCards, compCards, accountSignedIn);
    }

    // EFFECTS: continues playing game from file and keeps track of each side's cards.
    //          Then, it deals cards to each player and runs the game accordingly
    public void continueGame(Account accountSignedIn) {
        runGame(playerCards, compCards, accountSignedIn);
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
            if (!isGamePlaying) {
                break;
            }
            isGamePlaying = cardApp.isGameQuitManually();
        }
        if (lastCardPlayed == null) {
            return;
        }
        this.playerCards.clear();
        this.compCards.clear();
        accountSignedIn.playedAGame();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accountSignedInUsername", cardApp.getAccountSignedIn().getUsername());
        json.put("playerCards", playerCardsToJson());
        json.put("compCards", compCardsToJson());
        return json;
    }

    private JSONArray playerCardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : playerCards) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    private JSONArray compCardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : compCards) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public CardApp getCardApp() {
        return cardApp;
    }

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public List<Card> getCompCards() {
        return compCards;
    }
}
