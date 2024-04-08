package ui.options;

import model.Card;
import model.CardHandler;
import ui.CardApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

public class GameSetUpWindow extends JFrame implements ActionListener {
    private static int TEXT_AND_BUTTON_WIDTH = 10;
    private static int TEXT_AND_BUTTON_HEIGHT = 40;

    private JButton addCardButton;
    private JButton saveButton;

    private List<Card> playerCards;
    private Card lastCardPlayed;

    private CardHandler cardHandler;
    private CardApp cardApp;

    public GameSetUpWindow(List<Card> playerCards, Card lastCardPlayed) {
        initializeFields(playerCards, lastCardPlayed);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        addCardButton = new JButton("Play");
        addCardButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        JTextArea label = makeLabel("Opponent:");
        if (lastCardPlayed == null) {
            makeLabel("Opponent last card played:");
        } else {
            makeLabel("Opponent last card played: "
                    + lastCardPlayed.getIntNum() + " of " + lastCardPlayed.getSuit() + "\n");
        }
        makeLabel("Your cards:");

        add(label);
        sortCardForPlayer(playerCards);
        add(addCardButton);
        add(saveButton);
        pack();
        setVisible(true);

        ImageIcon image = new ImageIcon("Card Logo.png");
        this.setIconImage(image.getImage());
    }

    private void initializeFields(List<Card> playerCards, Card lastCardPlayed) {
        this.cardHandler = new CardHandler();
        this.playerCards = playerCards;
        this.lastCardPlayed = lastCardPlayed;
        this.cardApp = new CardApp("test");
    }

    private void sortCardForPlayer(List<Card> playerCards) {
        cardHandler.sortCardsForPlayer(playerCards);
        Map<Integer, Card> sortedCards = cardHandler.getSortedCardsMap();
        JTextArea sortedCardsLabel = new JTextArea();
        for (Integer cardNum : sortedCards.keySet()) {
            sortedCardsLabel.append(cardNum + " of " + sortedCards.get(cardNum).getSuit() + "\n");
        }
        sortedCardsLabel.setEditable(false);
        add(sortedCardsLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardButton) {
            JFrame addCardWindow = new JFrame();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Card newCard = new Card();
            JLabel addCardLabel = new JLabel("Added in " + newCard.getNum() + "of " + newCard.getSuit());
            playerCards.add(newCard);
            addCardWindow.add(addCardLabel);
            addCardWindow.pack();
            addCardWindow.setVisible(true);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            new GameSetUpWindow(playerCards, lastCardPlayed);
        }
        if (e.getSource() == saveButton) {
            cardApp.saveGame();
        }
    }


    public JTextArea makeLabel(String msg) {
        JTextArea label = new JTextArea();
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

        label.setText(msg);
        label.setEditable(false);
        add(label);
        return label;
    }
}

