package ui;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import model.Account;

public class CardApp {
    private Scanner input;
    private List<Account> accountList;

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
        System.out.println("Welcome to Da Niang Niang!\n");
        System.out.println("\t  New Game [1]");
        System.out.println("\t  Sign In [2]");
        System.out.println("  Create New Account [3]");
        System.out.println("\t Leaderboard [4]");
        System.out.println("\t  My Account [5]");
        System.out.println("\t  Quit Game [q]");
    }

    private void executeCommand(String command) {
        if (command.equals("1")) {
            newGame();
        } else if (command.equals("2")) {
            signIn();
        } else if (command.equals("3")) {
            createNewAcc();
        } else if (command.equals("4")) {
            showLeaderboard();
        } else if (command.equals("5")) {
            showMyAcc();
        } else {
            System.out.println("Please select a valid option!");
        }
    }

    private void newGame() {
        newGame();
    }

    private void signIn() {

    }

    private void createNewAcc() {

    }

    private void showLeaderboard() {

    }

    private void showMyAcc() {
    }
}
