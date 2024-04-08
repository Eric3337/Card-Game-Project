package ui.options;

import model.Account;
import model.AccountList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Map;

public class LeaderboardWindow extends JFrame implements ActionListener {
    private static int TEXT_AND_BUTTON_WIDTH = 10;
    private static int TEXT_AND_BUTTON_HEIGHT = 40;

    private JLabel crown;
    private JButton okayButton;

    public LeaderboardWindow(Map<String, Double> sortedLeaderBoard) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        okayButton = new JButton("Okay");
        okayButton.addActionListener(this);

        JTextArea label = new JTextArea();
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

        crown = makeCrown();

        String labelText = "";
        int i = 1;
        for (String username : sortedLeaderBoard.keySet()) {
            labelText += i++ + ": " + username + " = " + sortedLeaderBoard.get(username) + "\n";
        }
        label.setText(labelText);
        label.setEditable(false);

        add(crown);
        add(label);
        add(okayButton);
        pack();
        setVisible(true);

        ImageIcon image = new ImageIcon("Card Logo.png");
        this.setIconImage(image.getImage());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okayButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private JLabel makeCrown() {
        ImageIcon crownImage = new ImageIcon("crown.jpg");
        crown = new JLabel(crownImage);
        return crown;
    }
}

