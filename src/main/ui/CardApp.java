package ui;

import java.sql.Array;
import java.util.*;

import model.Account;
import model.Card;

public class CardApp {
    private Scanner input;
    private List<Account> accountList;
    private Account accountSignedIn;

    // EFFECTS: runs the CardApp
    public CardApp() {
        runApp();
    }

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

    private void init() {
        accountList = new ArrayList<Account>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayMainMenu() {
        System.out.println("---------------------------------------\nWelcome to Da Niang Niang!");
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

    private void newGame() {
        Random random = new Random();
        int numCardsPerPlayer = random.nextInt(4) + 2; // REMEMBER TO CHANGE BACK TO (11) + 10 !!!

        List<Card> playerCards = new ArrayList<Card>();
        List<Card> compCards = new ArrayList<Card>();

        dealCards(numCardsPerPlayer, playerCards, compCards);

        runGame(playerCards, compCards);
    }

    private void runGame(List<Card> playerCards, List<Card> compCards) {
        boolean isGamePlaying = true;
        Card lastCardPlayed = null;

        while (isGamePlaying) {
            gameSetUp(playerCards, lastCardPlayed);

            System.out.println("What do you want to play?");
            System.out.println("Number: ");
            String selectedNum = input.next();
            System.out.println("Suit: ");
            String selectedSuit = input.next();

            doTurns(playerCards, compCards, selectedNum, selectedSuit, lastCardPlayed);


            if (playerCards.size() == 0 || compCards.size() == 0) {
                isGamePlaying = false;
                if (playerCards.size() == 0) {
                    accountSignedIn.wonAGame();
                    System.out.println("Yay you won!");
                } else {
                    accountSignedIn.lostAGame();
                    System.out.println("Aww you lost!");
                }
            }
            accountSignedIn.playedAGame();
        }
    }

    private void gameSetUp(List<Card> playerCards, Card lastCardPlayed) {
        List<String> readableCards = new ArrayList<String>();
        for (int i = 0; i < playerCards.size(); i++) {
            readableCards.add(playerCards.get(i).getNum() + " of " + playerCards.get(i).getSuit());
        }
        Collections.sort(readableCards);
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

    private void doTurns(List<Card> pcards, List<Card> ccards, String selNum, String selSuit, Card lastCardPlayed) {
        for (int i = 0; i < pcards.size(); i++) {
            Card cardSelected = pcards.get(i);
            if (cardSelected.getNum().equals(selNum) && cardSelected.getSuit().equals(selSuit)) {
                pcards.remove(cardSelected);
                lastCardPlayed = cardSelected;
                break;
            }
        }

        for (int i = 0; i < ccards.size(); i++) {
            Card cardSelByComp = ccards.get(i);
            if (cardSelByComp.getIntNum() > (lastCardPlayed.getIntNum())) {
                ccards.remove(cardSelByComp);
                lastCardPlayed = cardSelByComp;
                break;
            }
        }
    }

    private void dealCards(int numCardsPerPlayer, List<Card> playerCards, List<Card> compCards) {

        for (int i = 0; i < numCardsPerPlayer; i++) {
            Card randPCard = new Card();
            Card randCCard = new Card();

            if (playerCards.size() == 0) {
                playerCards.add(randPCard);
                compCards.add(randCCard);
            }

            checkOrDealPlayerCards(playerCards, randPCard);

            checkOrDealOpponentCards(compCards, randCCard);


        }
        checkValidNumOfCards(numCardsPerPlayer, playerCards, compCards);

    }

    private void checkOrDealPlayerCards(List<Card> playerCards, Card randPCard) {
        for (int j = 0; j < playerCards.size(); j++) {
            Card currentCard = playerCards.get(j);
            int currentCardNum = currentCard.getIntNum();
            String currentCardSuit = currentCard.getSuit();
            if (randPCard.getIntNum() == currentCardNum && randPCard.getSuit().equals(currentCardSuit)) {
                break;
            } else {
                playerCards.add(randPCard);
                break;
            }
        }
    }

    private void checkOrDealOpponentCards(List<Card> compCards, Card randCCard) {
        for (int j = 0; j < compCards.size(); j++) {
            Card currCard = compCards.get(j);
            int currentCardNum = currCard.getIntNum();
            String currentCardSuit = currCard.getSuit();
            if (randCCard.getIntNum() == currentCardNum && randCCard.getSuit().equals(currentCardSuit)) {
                break;
            } else {
                compCards.add(randCCard);
                break;
            }
        }
    }


    private void checkValidNumOfCards(int numCardsPerPlayer, List<Card> playerCards, List<Card> compCards) {
        while (playerCards.size() != numCardsPerPlayer || compCards.size() != numCardsPerPlayer) {
            Card randPCard = new Card();
            Card randCCard = new Card();
            if (playerCards.size() != numCardsPerPlayer) {
                checkOrDealPlayerCards(playerCards, randPCard);
            }

            if (compCards.size() != numCardsPerPlayer) {
                checkOrDealOpponentCards(compCards, randCCard);
            }
        }
    }

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

    private void showLeaderboard() {
        List<Double> wlrList = new ArrayList<Double>();
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

    private void showMyAcc() {
        System.out.println("Total Games Played: " + accountSignedIn.getTotalGamesPlayed());
        System.out.println("W/L Ratio: " + accountSignedIn.calculateRatio());
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
