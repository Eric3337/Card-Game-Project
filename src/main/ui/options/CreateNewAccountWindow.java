package ui.options;

import model.Account;
import model.AccountList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class CreateNewAccountWindow extends JFrame implements ActionListener {
    private static int TEXT_AND_BUTTON_WIDTH = 10;
    private static int TEXT_AND_BUTTON_HEIGHT = 40;

    private Account accountSignedIn;
    private AccountList accountList;
    private JButton submit;
    private JTextField inputtedUsername;
    private JTextField inputtedPassword;

    private JFrame newAccountError;
    private JButton okayButton;

    public CreateNewAccountWindow(Account accountSignedIn, AccountList accountList) {
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
            createAccount();
        }

        if (e.getSource() == okayButton) {
            newAccountError.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void createAccount() {
        if (accountList.getAccountList().size() > 0) {
            boolean isValidUsername = false;

            while (!isValidUsername) {
                for (Account account : accountList.getAccountList()) {
                    String ithUsername = account.getUsername();
                    if (inputtedUsername.getText().equals(ithUsername)) {
                        newAccountUsernameTakenWindow();
                        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                        return;
                    } else {
                        isValidUsername = true;
                    }
                }
            }
        }
        Account userAccount = new Account(inputtedUsername.getText(), inputtedPassword.getText());
        accountList.getAccountList().add(userAccount);
        accountSignedIn = userAccount;
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }


    private void newAccountUsernameTakenWindow() {
        newAccountError = new JFrame();
        newAccountError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newAccountError.setLayout(new GridLayout(2, 1));

        okayButton = new JButton("Okay");
        okayButton.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));
        okayButton.addActionListener(this);

        JLabel label = new JLabel("That username has been taken. Try again!");
        label.setPreferredSize(new Dimension(TEXT_AND_BUTTON_WIDTH, TEXT_AND_BUTTON_HEIGHT));

        newAccountError.add(label);
        newAccountError.add(okayButton);

        newAccountError.pack();
        newAccountError.setVisible(true);
    }

    public Account getAccountSignedIn() {
        return accountSignedIn;
    }
}
