package model;

import java.util.Random;

public class Game {
    private int numCardsPerPlayer;
    private boolean playerWon;
    private int gameLevel;
    private static int gameNextId = 1;

    // EFFECTS: creates a new game with a random num of cards per player
    public Game(Player player) {
        Random random = new Random();
        numCardsPerPlayer = random.nextInt(11) + 10;
    }

    //EFFECTS:
    // there is getter for getting num of cards in each player's hand
    public void runGame() {
//        dealOutCards(); //deal cards to both players
//        while () {// while both opponent and user have cards in hand
//            playerGoes();

    }

    //}

    public void advanceGameLevel() {
    }

    // EFFECTS: if player or op has run out of cards, player has run and return true
    public boolean isPlayerWon() {
        return false;
    }

    public int getNumCardsPerPlayer() {
        return 0;
    }



    public int getGameLevel() {
        return 0;
    }


}
