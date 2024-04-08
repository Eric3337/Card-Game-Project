package ui.options;

import model.Account;
import model.AccountList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class NoPlayerOnLeaderboardWindow extends JFrame implements ActionListener {
    private static int TEXT_AND_BUTTON_WIDTH = 10;
    private static int TEXT_AND_BUTTON_HEIGHT = 40;

    private JButton okayButton;

    public NoPlayerOnLeaderboardWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        okayButton = new JButton("Okay");
        okayButton.addActionListener(this);

        JLabel label = new JLabel("No players on the leaderboard!");
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

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
}

