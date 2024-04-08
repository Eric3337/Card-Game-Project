package model;

import ui.MessagePrinter;
import ui.options.LeaderboardWindow;
import ui.options.NoPlayerOnLeaderboardWindow;

import java.util.*;

public class LeaderBoardSorter {
    private AccountList accountList;
    private EventLog eventLog;


    public LeaderBoardSorter() {
        eventLog = EventLog.getInstance();
    }

    public void sortLeaderBoard(AccountList accList, Account accSignedIn) {
        this.accountList = accList;

        if (accountList.getAccountList().size() == 0) {
            new NoPlayerOnLeaderboardWindow();
            return;
        }

        eventLog.logEvent(new Event("List of accounts used for ordering accounts by win loss ratio"));
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
