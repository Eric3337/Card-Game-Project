package ui;


import java.util.*;

import model.Account;
import model.Card;

// CardApp is the card game application. It runs the main menu and the other features such as signing in to an account
//      creating an account, looking up the leaderboards, and checking account statistic. It also runs games
//      and tracks an account's losses and wins.
public class CardApp {
    // General structure of displaying the main menu and getting the input from the user
    // was partially taken from the TellerApp that was provided in the edX phase 1 module
    private Scanner input;
    private List<Account> accountList;
    private Account accountSignedIn;

    // EFFECTS: creates a card app runs the CardApp
    public CardApp() {
        runApp();
    }

    // EFFECTS: run's the card game app
    private void runApp() {
        boolean isRunning = true;
        String command = null;

        init();

        while (isRunning) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                isRunning = false;
            } else {
                executeCommand(command);
            }
        }

        System.out.println("Thank you for playing!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields (accountSignedIn was not initialized here since
    //          it needs to be null at first
    private void init() {
        accountList = new ArrayList<Account>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays the main menu of the game
    private void displayMainMenu() {
        System.out.println("---------------------------------------\n Welcome to Da Niang Niang!");
        if (accountSignedIn == null) {
            System.out.println(" ");
        } else {
            System.out.println("\n     Welcome " + accountSignedIn.getUsername() + "!\n");
        }
        System.out.println("\t  New Game [1]");
        System.out.println("\t  Sign In [2]");
        System.out.println("  Create New Account [3]");
        System.out.println("\t Leaderboard [4]");
        System.out.println("\t  My Account [5]");
        System.out.println("\t  Quit Game [q]\n---------------------------------------");
    }

    // EFFECTS: executes the main menu command and calls a method to whichever command is desired
    private void executeCommand(String command) {
        if (command.equals("1")) {
            if (accountSignedIn == null) {
                System.out.println("Please sign in or create a new account!");
            } else {
                newGame();
            }
        } else if (command.equals("2")) {
            signIn();
        } else if (command.equals("3")) {
            createNewAcc();
        } else if (command.equals("4")) {
            showLeaderboard();
        } else if (command.equals("5")) {
            if (accountSignedIn == null) {
                System.out.println("Please sign in or create a new account!");
            } else {
                showMyAcc();
            }
        } else {
            System.out.println("Please select a valid option!");
        }
    }

    // EFFECTS: initializes a new game that creates a random number of cards for the player
    //          and opponent from 11 to 20, and keeps track of each side's cards.
    //          Then, it deals cards to each player and runs the game accordingly
    private void newGame() {
        Random random = new Random();
        int numCardsPerPlayer = random.nextInt(11) + 10;

        List<Card> playerCards = new ArrayList<Card>();
        List<Card> compCards = new ArrayList<Card>();

        dealCards(numCardsPerPlayer, playerCards, compCards);

        runGame(playerCards, compCards);
    }

    // EFFECTS: runs a game of cards based off the rules of the game and which cards the player has
    //          selected to play down
    //          if either the player or opponent (computer opponent) runs out of cards,
    //          print out appropriate message and add a victory or loss to player accordingly
    //          then adds one game to total games played
    private void runGame(List<Card> playerCards, List<Card> compCards) {
        boolean isGamePlaying = true;
        Card lastCardPlayed = null;

        while (isGamePlaying) {
            gameSetUp(playerCards, lastCardPlayed);

            lastCardPlayed = executeGameInteraction(playerCards, compCards, lastCardPlayed);

            isGamePlaying = gameOver(playerCards, compCards);
        }
        accountSignedIn.playedAGame();
    }

    // EFFECTS: based off the commands by the user in the game, the user can either pass or play
    private Card executeGameInteraction(List<Card> playerCards, List<Card> compCards, Card lastCardPlayed) {
        System.out.println("\nPass [1] or Play [2]");
        String gameCommand = input.next();
        if (gameCommand.equals("1")) {
            Card lowestCardNum = compCards.get(0);
            for (int i = 1; i < compCards.size(); i++) {
                if (compCards.get(i).getIntNum() < lowestCardNum.getIntNum()) {
                    lowestCardNum = compCards.get(i);
                }
            }
            compCards.remove(compCards.get(compCards.indexOf(lowestCardNum)));
            lastCardPlayed = lowestCardNum;
        } else if (gameCommand.equals("2")) {
            System.out.println("Number: ");
            String selectedNum = input.next();
            System.out.println("Suit: ");
            String selectedSuit = input.next();

            lastCardPlayed = doPTurn(playerCards, selectedNum, selectedSuit, lastCardPlayed, compCards);
        } else {
            System.out.println("Please select a valid option!");
        }
        return lastCardPlayed;
    }

    // EFFECTS: checks to see if the game is over and modifies the fields in the signed in accounts according to
    //          whether the account has won or lost the game
    private boolean gameOver(List<Card> playerCards, List<Card> compCards) {
        if (playerCards.size() == 0 || compCards.size() == 0) {
            if (playerCards.size() == 0) {
                accountSignedIn.wonAGame();
                System.out.println("Yay you won!");
            } else {
                accountSignedIn.lostAGame();
                System.out.println("Aww you lost!");
            }
            return false;
        }
        return true;
    }

    // EFFECTS: sets up game and the intermediate phases of the game where the player is
    //          asked to choose a card to play and displays the last card played down and
    //          lists the cards that the player has
    private void gameSetUp(List<Card> playerCards, Card lastCardPlayed) {
        List<String> readableCards = new ArrayList<String>();
        for (int i = 0; i < playerCards.size(); i++) {
            readableCards.add(playerCards.get(i).getNum() + " of " + playerCards.get(i).getSuit());
        }
        System.out.println("--------------------\nOpponent\n");
        if (lastCardPlayed == null) {
            System.out.println("Last card played: \n");
        } else {
            System.out.println("Last card played: " + lastCardPlayed.getIntNum() + " of " + lastCardPlayed.getSuit());
        }
        System.out.println("\nYour cards: ");

        for (int i = 0; i < readableCards.size(); i++) {
            System.out.println(readableCards.get(i));
        }
    }

    // EFFECTS: does the player's turn checks to see whether the card selected by player exists in hand and
    //          whether selNum > lastCardPlayed.getIntNum()
    private Card doPTurn(List<Card> pcards, String selNum, String selSuit, Card lastCardPlayed, List<Card> ccards) {
        for (int i = 0; i < pcards.size(); i++) {
            if (pcards.get(i).getNum().equals(selNum) && pcards.get(i).getSuit().equals(selSuit)) {
                if (lastCardPlayed == null) {
                    lastCardPlayed = pcards.get(i);
                    pcards.remove(pcards.get(i));
                    lastCardPlayed = doCompTurn(ccards, lastCardPlayed);
                    return lastCardPlayed;
                }
                if (Integer.parseInt(selNum) > lastCardPlayed.getIntNum()) {
                    lastCardPlayed = pcards.get(i);
                    pcards.remove(pcards.get(i));
                    lastCardPlayed = doCompTurn(ccards, lastCardPlayed);
                    return lastCardPlayed;
                }
                System.out.println("Please select a card number greater than last played card!");
                return lastCardPlayed;
            }
        }
        System.out.println("Please select a card in your hand!");
        return lastCardPlayed;
    }

    // EFFECTS: does the computer turn by checking to see if there is a larger card than the last played card
    //          and plays that card, otherwise opponent will print out "pass"
    private Card doCompTurn(List<Card> ccards, Card lastCardPlayed) {
        if (lastCardPlayed == null) {
            return null;
        } else {
            for (int i = 0; i < ccards.size(); i++) {
                if (ccards.get(i).getIntNum() > lastCardPlayed.getIntNum()) {
                    lastCardPlayed = ccards.get(i);
                    ccards.remove(ccards.get(i));
                    return lastCardPlayed;
                }
            }
        }
        System.out.println("Opponent: Pass :(");
        return null;
    }

    // EFFECTS: deals cards to both computer opponent and player ensuring that there are the correct number of
    //          cards for each player and with no duplicates in each deck
    private void dealCards(int numCardsPerPlayer, List<Card> playerCards, List<Card> compCards) {

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

    // EFFECTS checks whether a card is in a hand
    private boolean isRandCardInHand(List<Card> cardsInHand, Card randomCard) {
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

    // EFFECTS: signs in a user into their account
    private void signIn() {
        System.out.println("Username: ");
        String inputtedUsername = input.next();
        System.out.println("Password:");
        String inputtedPassword = input.next();
        for (int i = 0; i < accountList.size(); i++) {
            Account ithAccount = accountList.get(i);
            String ithUsername = ithAccount.getUsername();
            String ithPw = ithAccount.getPw();
            if (ithUsername.equals(inputtedUsername) && ithPw.equals(inputtedPassword)) {
                System.out.println("Thank you for signing in!");
                accountSignedIn = ithAccount;
                return;
            }
        }
        System.out.println("Username does not exist, or incorrect password!");
    }

    // EFFECTS: creates a new account based off a user's username and password
    private void createNewAcc() {
        System.out.println("Username: ");
        String inputtedUsername = input.next();

        if (accountList.size() > 0) {
            boolean isValidUsername = false;

            while (!isValidUsername) {
                for (int i = 0; i < accountList.size(); i++) {
                    Account ithAccount = accountList.get(i);
                    String ithUsername = ithAccount.getUsername();
                    if (inputtedUsername.equals(ithUsername)) {
                        System.out.println("That username has been taken. Try again!");
                        return;
                    } else {
                        isValidUsername = true;
                    }
                }
            }
        }
        System.out.println("Password: ");
        String password = input.next();
        Account userAccount = new Account(inputtedUsername, password);
        accountList.add(userAccount);
        accountSignedIn = userAccount;
    }

    // EFFECTS: show the leaderboard with the user's position on the leaderboard, with the option to return back
    //          to the main menu
    private void showLeaderboard() {
        List<Double> wlrList = new ArrayList<>();
        if (accountList.size() == 0) {
            System.out.println("No players on leaderboard yet!");
            return;
        }
        for (int i = 0; i < accountList.size(); i++) {
            double ithWinLossRatio = accountList.get(i).calculateRatio();
            wlrList.add(ithWinLossRatio);
        }
        Collections.sort(wlrList);
        Collections.reverse(wlrList);
        System.out.println(wlrList);
        int accPos = wlrList.indexOf(accountSignedIn.calculateRatio()) + 1;
        System.out.println("My position is: " + accPos);
        System.out.println("\nBack [b]");

        showLeaderboardMenu();
    }

    // EFFECTS: shows the leaderboard menu and takes in user command
    private void showLeaderboardMenu() {
        boolean isLeaderboardRunning = true;
        while (isLeaderboardRunning) {
            String leaderboardCommand = input.next();
            if (leaderboardCommand.equals("b")) {
                isLeaderboardRunning = false;
            } else {
                System.out.println("Please select a valid option!");
            }
        }
    }

    // EFFECTS: shows the user's account with their total games played, win loss ratio, games won/lost, as well as
    //          the option to delete, sign out, or go back to main menu option
    private void showMyAcc() {
        System.out.println("Total Games Played: " + accountSignedIn.getTotalGamesPlayed());
        System.out.println("W/L Ratio: " + accountSignedIn.calculateRatio());
        System.out.println("Games Won: " + accountSignedIn.getGamesWon());
        System.out.println("Games Lost: " + accountSignedIn.getGamesLost());
        System.out.println("\nDelete Account [d]");
        System.out.println("Sign Out [s]\n");
        System.out.println("Back [b]");

        boolean isMyAccRunning = true;

        while (isMyAccRunning) {
            String accountCommand = input.next();
            if (accountCommand.equals("d")) {
                accountList.remove(accountSignedIn);
                accountSignedIn = null;
                isMyAccRunning = false;
            } else if (accountCommand.equals("s")) {
                accountSignedIn = null;
                isMyAccRunning = false;
            } else if (accountCommand.equals("b")) {
                return;
            } else {
                System.out.println("Please select a valid option!");
            }
        }
    }
}
