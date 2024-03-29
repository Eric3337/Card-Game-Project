package ui.options;

import model.Account;
import model.AccountList;
import ui.DaNiangNiang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class SignInWindow extends JFrame implements ActionListener {
    private static int TEXT_AND_BUTTON_WIDTH = 10;
    private static int TEXT_AND_BUTTON_HEIGHT = 40;

    private Account accountSignedIn;
    private AccountList accountList;
    private JButton submit;
    private JTextField inputtedUsername;
    private JTextField inputtedPassword;

    private JFrame signInError;
    private JButton okayButton;

    public SignInWindow(Account accountSignedIn, AccountList accountList) {
        this.accountSignedIn = accountSignedIn;
        this.accountList = accountList;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        submit = new JButton("Submit");
        submit.addActionListener(this);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));
        inputtedUsername = new JTextField();
        inputtedUsername.setPreferredSize(new Dimension(250, TEXT_AND_BUTTON_HEIGHT));

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));
        inputtedPassword = new JTextField();
        inputtedPassword.setPreferredSize(new Dimension(250, TEXT_AND_BUTTON_HEIGHT));

        add(usernameLabel);
        this.add(inputtedUsername);
        add(passwordLabel);
        add(inputtedPassword);
        add(submit);
        pack();
        setVisible(true);

        ImageIcon image = new ImageIcon("Card Logo.png");
        this.setIconImage(image.getImage());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            for (Account account : accountList.getAccountList()) {
                String ithUsername = account.getUsername();
                String ithPw = account.getPw();
                if (ithUsername.equals(inputtedUsername.getText()) && ithPw.equals(inputtedPassword.getText())) {
                    System.out.println("Thank you for signing in!");
                    accountSignedIn = account;
                }
            }
            signInErrorWindow();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if (e.getSource() == okayButton) {
            signInError.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void signInErrorWindow() {
        signInError = new JFrame();
        signInError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signInError.setLayout(new GridLayout(2, 1));

        okayButton = new JButton("Okay");
        okayButton.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));
        okayButton.addActionListener(this);

        JLabel label = new JLabel("Username does not exist, or incorrect password!");
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

        signInError.add(label);
        signInError.add(okayButton);

        signInError.pack();
        signInError.setVisible(true);
    }

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }
}
