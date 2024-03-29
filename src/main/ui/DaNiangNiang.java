package ui;

import model.*;
import ui.options.CreateNewAccountWindow;
import ui.options.SignInWindow;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DaNiangNiang extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 750;

    private static final String JSON_STORE_GAME = "./data/cardgame.json";
    private static final String JSON_STORE_ACCOUNTS = "./data/accountList.json";
    private Scanner input;

    private AccountList accountList;
    private Account accountSignedIn;

    private CardGame cardGame;
    private CardApp cardApp;

    private CardHandler cardHandler;
    private TurnHandler turnHandler;
    private LeaderBoardSorter leaderBoardSorter;
    private MessagePrinter msgPrinter;

    private JsonWriter jsonWriterGame;
    private JsonReader jsonReaderGame;
    private JsonWriter jsonWriterAccounts;
    private JsonReader jsonReaderAccounts;

    private JButton playButton;
    private JButton signInButton;
    private JButton createNewAccountButton;
    private JButton leaderBoardButton;
    private JButton myAccountButton;
    private JButton quitButton;

    public DaNiangNiang() {
        super("Da Niang Niang");
        initializeFields();
        setJFrameSettings();
    }

    private void initializeFields() {
        this.accountList = new AccountList();
        this.input = new Scanner(System.in);
        this.input.useDelimiter("\n");
        this.cardGame = new CardGame(accountSignedIn, cardApp);
        this.cardHandler = new CardHandler();
        this.turnHandler = new TurnHandler();
        this.leaderBoardSorter = new LeaderBoardSorter();
        this.msgPrinter = new MessagePrinter();

        this.jsonWriterGame = new JsonWriter(JSON_STORE_GAME);
        this.jsonReaderGame = new JsonReader(JSON_STORE_GAME);
        this.jsonWriterAccounts = new JsonWriter(JSON_STORE_ACCOUNTS);
        this.jsonReaderAccounts = new JsonReader(JSON_STORE_ACCOUNTS);
    }

    private void setJFrameSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Da Niang Niang");
        setLayout(new FlowLayout(FlowLayout.CENTER));

        makeButtons();
        setVisible(true);

        ImageIcon image = new ImageIcon("Card Logo.png");
        this.setIconImage(image.getImage());
    }

    private void makeButtons() {
        JPanel menuPanel = new JPanel(new GridLayout(6, 1));
        menuPanel.setBounds(FRAME_WIDTH / 2 - FRAME_WIDTH / 8, FRAME_HEIGHT / 4,
                FRAME_WIDTH / 4, FRAME_HEIGHT / 2);
        add(menuPanel, BorderLayout.CENTER);

        makePlayButton();
        menuPanel.add(playButton);

        makeSignInButton();
        menuPanel.add(signInButton);

        makeCreateAccountButton();
        menuPanel.add(createNewAccountButton);

        makeLeaderBoardButton();
        menuPanel.add(leaderBoardButton);

        makeMyAccountButton();
        menuPanel.add(myAccountButton);

        makeQuitButton();
        menuPanel.add(quitButton);
    }

    private void makePlayButton() {
        playButton = new JButton("Play");
        playButton.setBounds(195, 150, 100, 50);
        playButton.addActionListener(this);
        playButton.setFocusable(false);
    }

    private void makeSignInButton() {
        signInButton = new JButton("Sign In");
        signInButton.setBounds(195, 200, 100, 50);
        signInButton.addActionListener(this);
        signInButton.setFocusable(false);
    }

    private void makeCreateAccountButton() {
        createNewAccountButton = new JButton("Create New Account");
        createNewAccountButton.setBounds(169, 250, 149, 50);
        createNewAccountButton.addActionListener(this);
        createNewAccountButton.setFocusable(false);
    }

    private void makeLeaderBoardButton() {
        leaderBoardButton = new JButton("Add Account to Data");
        leaderBoardButton.setBounds(169, 300, 149, 50);
        leaderBoardButton.addActionListener(this);
        leaderBoardButton.setFocusable(false);
    }

    private void makeMyAccountButton() {
        myAccountButton = new JButton("My Account");
        myAccountButton.setBounds(169, 350, 149, 50);
        myAccountButton.addActionListener(this);
        myAccountButton.setFocusable(false);
    }

    private void makeQuitButton() {
        quitButton = new JButton("Quit Game");
        quitButton.setBounds(169, 400, 149, 50);
        quitButton.addActionListener(this);
        quitButton.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            System.out.println("Playing game");
        }
        if (e.getSource() == signInButton) {
            signIn();
        }
        if (e.getSource() == createNewAccountButton) {
            createNewAccount();
        }
        if (e.getSource() == leaderBoardButton) {
            System.out.println("Showing leaderboard");
        }
        if (e.getSource() == myAccountButton) {
            System.out.println("Showing your account");
        }
        if (e.getSource() == quitButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    // EFFECTS: signs in a user into their account
    private void signIn() {
        SignInWindow sw = new SignInWindow(accountSignedIn, accountList);

        if (sw.getAccountSignedIn() != null) {
            JLabel accountSignedInLabel = new JLabel();
            accountSignedInLabel.setText("Welcome " + sw.getAccountSignedIn().getUsername() + "!");
        }
        this.accountSignedIn = sw.getAccountSignedIn();
    }

    // EFFECTS: creates a new account based off a user's username and password
    private void createNewAccount() {
        CreateNewAccountWindow window = new CreateNewAccountWindow(accountSignedIn, accountList);

        if (window.getAccountSignedIn() != null) {
            JLabel accountSignedInLabel = new JLabel();
            accountSignedInLabel.setText("Welcome " + window.getAccountSignedIn().getUsername() + "!");
        }
//        System.out.println("Username: ");
//        String inputtedUsername = input.next();
//
//        if (accountList.getAccountList().size() > 0) {
//            boolean isValidUsername = false;
//
//            while (!isValidUsername) {
//                for (Account account : accountList.getAccountList()) {
//                    String ithUsername = account.getUsername();
//                    if (inputtedUsername.equals(ithUsername)) {
//                        System.out.println("That username has been taken. Try again!");
//                        return;
//                    } else {
//                        isValidUsername = true;
//                    }
//                }
//            }
//        }
//        System.out.println("Password: ");
//        String password = input.next();
//        Account userAccount = new Account(inputtedUsername, password);
//        accountList.getAccountList().add(userAccount);
//        accountSignedIn = userAccount;
    }

    public static void main(String[] args) {
        new DaNiangNiang();
        try {
            new CardApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }
}
