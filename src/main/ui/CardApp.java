package ui;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// CardApp is the card game application. It runs the main menu and the other features such as signing in to an account
//      creating an account, looking up the leaderboards, and checking account statistic. It also runs games
//      and tracks an account's losses and wins.
public class CardApp {
    // General structure of displaying the main menu and getting the input from the user
    // was partially taken from the TellerApp that was provided in the edX phase 1 module
    private static final String JSON_STORE_GAME = "./data/cardgame.json";
    private static final String JSON_STORE_ACCOUNTS = "./data/accountList.json";
    private Scanner input;

    private AccountList accountList;
    private Account accountSignedIn;
    private CardGame cardGame;
    private CardHandler cardHandler;
    private TurnHandler turnHandler;
    private LeaderBoardSorter leaderBoardSorter;
    private MessagePrinter msgPrinter;

    private JsonWriter jsonWriterGame;
    private JsonReader jsonReaderGame;
    private JsonWriter jsonWriterAccounts;
    private JsonReader jsonReaderAccounts;

    // EFFECTS: creates a card app runs the CardApp
    public CardApp() throws FileNotFoundException {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields (accountSignedIn was not initialized here since
    //          it needs to be null at first
    private void init() {
        this.accountList = new AccountList();
        this.input = new Scanner(System.in);
        this.input.useDelimiter("\n");
        this.cardGame = new CardGame(accountSignedIn, this);
        this.cardHandler = new CardHandler();
        this.turnHandler = new TurnHandler();
        this.leaderBoardSorter = new LeaderBoardSorter();
        this.msgPrinter = new MessagePrinter();

        this.jsonWriterGame = new JsonWriter(JSON_STORE_GAME);
        this.jsonReaderGame = new JsonReader(JSON_STORE_GAME);
        this.jsonWriterAccounts = new JsonWriter(JSON_STORE_ACCOUNTS);
        this.jsonReaderAccounts = new JsonReader(JSON_STORE_ACCOUNTS);
    }

    // EFFECTS: sets account list to given account list
    public void setAccountList(AccountList accList) {
        this.accountList = accList;
    }

    // EFFECTS: run's the card game app
    private void runApp() {
        boolean isRunning = true;
        String command;

        init();

        loadAccountList();

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
        saveAccountList();
    }

    // EFFECTS: displays the main menu of the game
    private void displayMainMenu() {
        System.out.println("---------------------------------------\n Welcome to Da Niang Niang!");
        if (accountSignedIn == null) {
            System.out.println(" ");
        } else {
            System.out.println("\n     Welcome " + accountSignedIn.getUsername() + "!\n");
        }
        System.out.println("\t    Play [1]");
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
                newOrContinueGame();
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

    private void newOrContinueGame() {
        System.out.println("Start new game [1]\nContinue game [2]");
        String command = input.next();
        if (command.equals("1")) {
            cardGame.newGame(accountSignedIn);
        } else if (command.equals("2")) {
            loadCardGame(this);
            cardGame.continueGame(accountSignedIn);
        } else {
            System.out.println("Please select a valid option!");
        }
    }

    // EFFECTS: based off the commands by the user in the game, the user can either pass or play
    public Card executeGameInteraction(List<Card> playerCards,
                                       List<Card> compCards, Card lastCardPlayed) {
        System.out.println("\nPass [1] or Play [2]");
        String gameCommand = input.next();
        if (gameCommand.equals("1")) {
            return cardHandler.compLowestCardToPlay(compCards);

        } else if (gameCommand.equals("2")) {
            System.out.println("Number: ");
            String selectedNum = input.next();
            System.out.println("Suit: ");
            String selectedSuit = input.next();

            return turnHandler.doPlayerAndCompTurn(playerCards, compCards, selectedNum,
                    selectedSuit, lastCardPlayed);
        } else {
            System.out.println("Please select a valid option!");
            return lastCardPlayed;
        }
    }

    public boolean isGameQuitManually() {
        System.out.println("\nKeep playing [p] or quit game? [q]");
        String gameCommand = input.next();
        if (gameCommand.equals("q")) {
            saveGame();
            return false;
        }
        return true;
    }

    // EFFECTS: saves the card game to file
    private void saveGame() {
        try {
            jsonWriterGame.open();
            jsonWriterGame.writeGame(cardGame);
            jsonWriterGame.close();
            System.out.println("Saved game to " + JSON_STORE_GAME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_GAME);
        }
    }

    // EFFECTS: saves the card game to file
    private void saveAccountList() {
        try {
            jsonWriterAccounts.open();
            jsonWriterAccounts.writeAccList(accountList);
            jsonWriterAccounts.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_ACCOUNTS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadCardGame(CardApp cardApp) {
        try {
            cardGame = jsonReaderGame.readCardGame(accountList, cardApp);
            System.out.println("Loaded previous played from " + JSON_STORE_GAME);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_GAME);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadAccountList() {
        try {
            accountList = jsonReaderAccounts.readAccountList(this);
            System.out.println("Loaded list of accounts " + JSON_STORE_ACCOUNTS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_ACCOUNTS);
        }
    }

    // EFFECTS: checks to see if the game is over and modifies the fields in the signed in accounts according to
    //          whether the account has won or lost the game
    public boolean gameOver(List<Card> playerCards, List<Card> compCards) {
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
    public void gameSetUp(List<Card> playerCards, Card lastCardPlayed) {
        System.out.println("--------------------\nOpponent\n");
        if (lastCardPlayed == null) {
            System.out.println("Opponent last card played: \n");
        } else {
            System.out.println("Opponent last card played: "
                    + lastCardPlayed.getIntNum() + " of " + lastCardPlayed.getSuit());
        }
        System.out.println("\nYour cards: ");

        cardHandler.sortCardsForPlayer(playerCards);
    }

    // EFFECTS: signs in a user into their account
    private void signIn() {
        System.out.println("Username: ");
        String inputtedUsername = input.next();
        System.out.println("Password:");
        String inputtedPassword = input.next();
        for (Account account : accountList.getAccountList()) {
            String ithUsername = account.getUsername();
            String ithPw = account.getPw();
            if (ithUsername.equals(inputtedUsername) && ithPw.equals(inputtedPassword)) {
                System.out.println("Thank you for signing in!");
                accountSignedIn = account;
                return;
            }
        }
        System.out.println("Username does not exist, or incorrect password!");
    }

    // EFFECTS: creates a new account based off a user's username and password
    private void createNewAcc() {
        System.out.println("Username: ");
        String inputtedUsername = input.next();

        if (accountList.getAccountList().size() > 0) {
            boolean isValidUsername = false;

            while (!isValidUsername) {
                for (Account account : accountList.getAccountList()) {
                    String ithUsername = account.getUsername();
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
        accountList.getAccountList().add(userAccount);
        accountSignedIn = userAccount;
    }

    // EFFECTS: show the leaderboard with the user's position on the leaderboard, with the option to return back
    //          to the main menu
    private void showLeaderboard() {
        leaderBoardSorter.sortLeaderBoard(accountList, accountSignedIn);
        showLeaderboardMenu();
    }

    // EFFECTS: shows the leaderboard menu and takes in user command
    private void showLeaderboardMenu() {
        msgPrinter.printMessage("\nBack [b]");

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
                accountList.getAccountList().remove(accountSignedIn);
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

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }
}
