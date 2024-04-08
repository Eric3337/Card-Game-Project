package model;

import ui.MessagePrinter;
import ui.options.LeaderboardWindow;
import ui.options.NoPlayerOnLeaderboardWindow;

import java.util.*;

public class LeaderBoardSorter {
    private MessagePrinter msgPrinter;

    private AccountList accountList;
    private Account accountSignedIn;


    public LeaderBoardSorter() {
        this.msgPrinter = new MessagePrinter();
    }

    public void sortLeaderBoard(AccountList accList, Account accSignedIn) {
        this.accountList = accList;
        this.accountSignedIn = accSignedIn;

        if (accountList.getAccountList().size() == 0) {
            new NoPlayerOnLeaderboardWindow();
            return;
        }

        orderAccounts(accountList);
    }

    // REQUIRES: accountList.getAccountList().size() != 0
    public void orderAccounts(AccountList accountList) {
        double prevWinLossRatio = accountList.getAccountList().get(0).calculateRatio();
        Map<String, Double> sortedLeaderBoard = new LinkedHashMap<>();

        sortedLeaderBoard.put(accountList.getAccountList().get(0).getUsername(), prevWinLossRatio);

        for (int i = 1; i < accountList.getAccountList().size(); i++) {
            Account account = accountList.getAccountList().get(i);
            double winLossRatio = account.calculateRatio();

            if (winLossRatio < prevWinLossRatio) {
                prevWinLossRatio = winLossRatio;

                orderByAscendingDoubleValues(prevWinLossRatio, sortedLeaderBoard, account);

            } else {
                sortedLeaderBoard.put(account.getUsername(), winLossRatio);
            }
        }

        new LeaderboardWindow(sortedLeaderBoard);
    }

    private void orderByAscendingDoubleValues(double prevWinLossRatio,
                                              Map<String, Double> sortedLeaderBoard, Account account) {
        Map<String, Double> tempMap = new HashMap<>(sortedLeaderBoard);
        sortedLeaderBoard.clear();
        sortedLeaderBoard.put(account.getUsername(), prevWinLossRatio);
        sortedLeaderBoard.putAll(tempMap);
    }
}
