package ui;

import model.*;
import model.Event;
import ui.options.CreateNewAccountWindow;
import ui.options.LeaderboardWindow;
import ui.options.PlayOrContinueWindow;
import ui.options.SignInWindow;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;


public class Main extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 750;

    private static final String JSON_STORE_GAME = "./data/cardgame.json";
    private static final String JSON_STORE_ACCOUNTS = "./data/accountList.json";

    private AccountList accountList;
    private Account accountSignedIn;

    private CardGame cardGame;
    private CardApp cardApp;

    private CardHandler cardHandler;
    private TurnHandler turnHandler;
    private LeaderBoardSorter leaderBoardSorter;

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

    private SignInWindow signInWindow;

    private EventLog eventLog;

    public Main() {
        super("Da Niang Niang");
        initializeFields();
        setJFrameSettings();
    }

    private void initializeFields() {
        this.accountList = new AccountList();
        this.accountSignedIn = new Account("eric", "123");
        this.cardGame = new CardGame(accountSignedIn, cardApp);
        this.cardHandler = new CardHandler();
        this.cardApp = new CardApp("test");
        this.turnHandler = new TurnHandler();
        this.leaderBoardSorter = new LeaderBoardSorter();

        this.jsonWriterGame = new JsonWriter(JSON_STORE_GAME);
        this.jsonReaderGame = new JsonReader(JSON_STORE_GAME);
        this.jsonWriterAccounts = new JsonWriter(JSON_STORE_ACCOUNTS);
        this.jsonReaderAccounts = new JsonReader(JSON_STORE_ACCOUNTS);

        this.eventLog = EventLog.getInstance();
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
        playButton = new JButton("Save Account");
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
        leaderBoardButton = new JButton("Leaderboard");
        leaderBoardButton.setBounds(169, 300, 149, 50);
        leaderBoardButton.addActionListener(this);
        leaderBoardButton.setFocusable(false);
    }

    private void makeMyAccountButton() {
        myAccountButton = new JButton("Load Accounts from List");
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
            saveAccount();
        }
        if (e.getSource() == signInButton) {
            signIn();
        }
        if (e.getSource() == createNewAccountButton) {
            createNewAccount();
        }
        if (e.getSource() == leaderBoardButton) {
            showLeaderboard();
        }
        if (e.getSource() == myAccountButton) {
            loadAccountList();
        }
        if (e.getSource() == quitButton) {
            for (Event event : eventLog) {
                System.out.println(event.getDescription() + " at "
                        + "(" + event.getDate() + ").");
            }
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void newOrContinueGame() {
        new PlayOrContinueWindow(accountSignedIn);
    }

    // EFFECTS: signs in a user into their account
    private void signIn() {
        signInWindow = new SignInWindow(accountSignedIn, accountList);
        //this.accountSignedIn = signInWindow.getAccountSignedIn();
        System.out.println(signInWindow.getAccountSignedIn());
    }

    // EFFECTS: creates a new account based off a user's username and password
    private void createNewAccount() {
        new CreateNewAccountWindow(accountSignedIn, accountList);

    }

    // EFFECTS: show the leaderboard with the user's position on the leaderboard, with the option to return back
    //          to the main menu
    private void showLeaderboard() {
        leaderBoardSorter.sortLeaderBoard(accountList, accountSignedIn);
    }

    private void saveAccount() {
        cardApp.saveAccountList(accountList);
    }

    private void loadAccountList() {
        accountList = cardApp.loadAccountList();
    }

    public static void main(String[] args) {
        new Main();
//        try {
//            new CardApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
    }

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }
}
