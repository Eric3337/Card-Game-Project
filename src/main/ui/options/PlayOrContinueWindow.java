package ui.options;

import model.Account;
import model.Card;
import model.CardGame;
import model.CardHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayOrContinueWindow extends JFrame implements ActionListener {
    private static int TEXT_AND_BUTTON_WIDTH = 10;
    private static int TEXT_AND_BUTTON_HEIGHT = 40;

    private static int MIN_NUM_OF_CARDS = 10;
    private static int MAX_NUM_OF_CARDS = 20;

    private Account accountSignedIn;

    private CardHandler cardHandler;
    private List<Card> playerCards;
    private List<Card> compCards;

    private JButton newGameButton;
    private JButton continueGameButton;
    private JFrame signInError;
    private JFrame signInSuccess;
    private JButton okayButtonError;
    private JButton okayButtonSuccess;

    public PlayOrContinueWindow(Account accountSignedIn) {
        this.accountSignedIn = accountSignedIn;
        this.cardHandler = new CardHandler();
        this.playerCards = new ArrayList<>();
        this.compCards = new ArrayList<>();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        newGameButton = new JButton("Start new game");
        newGameButton.addActionListener(this);

        continueGameButton = new JButton("Continue game");
        continueGameButton.addActionListener(this);

        add(newGameButton);
        add(continueGameButton);
        pack();
        setVisible(true);

        ImageIcon image = new ImageIcon("Card Logo.png");
        this.setIconImage(image.getImage());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            newGame(accountSignedIn);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if (e.getSource() == okayButtonError) {
            signInError.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if (e.getSource() == okayButtonSuccess) {
            signInSuccess.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void newGame(Account accountSignedIn) {
        Random random = new Random();
        int numCardsPerPlayer = random.nextInt(MAX_NUM_OF_CARDS - MIN_NUM_OF_CARDS) + MIN_NUM_OF_CARDS;

        cardHandler.dealCards(numCardsPerPlayer, playerCards, compCards);

        runGame(accountSignedIn);
    }

    private void runGame(Account accountSignedIn) {
        new GameSetUpWindow(playerCards, null);
    }

    private void signInErrorWindow() {
        signInError = new JFrame();
        signInError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signInError.setLayout(new GridLayout(2, 1));

        okayButtonError = new JButton("Okay");
        okayButtonError.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));
        okayButtonError.addActionListener(this);

        JLabel label = new JLabel("Username does not exist, or incorrect password!");
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

        signInError.add(label);
        signInError.add(okayButtonError);

        signInError.pack();
        signInError.setVisible(true);
    }

    private void signInSuccessWindow() {
        signInSuccess = new JFrame();
        signInSuccess.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signInSuccess.setLayout(new GridLayout(2, 1));

        okayButtonSuccess = new JButton("Okay");
        okayButtonSuccess.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));
        okayButtonSuccess.addActionListener(this);

        JLabel label = new JLabel("Thank you for signing in!");
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

        signInSuccess.add(label);
        signInSuccess.add(okayButtonSuccess);

        signInSuccess.pack();
        signInSuccess.setVisible(true);
    }

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }
}
