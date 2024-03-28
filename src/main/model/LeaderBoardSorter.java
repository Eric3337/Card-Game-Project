package model;

import ui.MessagePrinter;

import java.util.*;

public class LeaderBoardSorter {
    private MessagePrinter msgPrinter;

    public LeaderBoardSorter() {
        this.msgPrinter = new MessagePrinter();
    }

    public void sortLeaderBoard(AccountList accountList, Account accountSignedIn) {
        Map<Double, String> unsortedLeaderBoard = new HashMap<>();
        List<Double> ratioList = new ArrayList<>();
        if (accountList.getAccountList().size() == 0) {
            msgPrinter.printMessage("No players on leaderboard yet!");
            return;
        }

        orderAccounts(accountList, unsortedLeaderBoard, ratioList);

        if (accountSignedIn != null) {
            int accPos = ratioList.indexOf(accountSignedIn.calculateRatio()) + 1;
            msgPrinter.printMessage("\nMy position is: " + accPos);
        }
    }

    public void orderAccounts(AccountList accountList, Map<Double, String> unsortedLeaderBoard,
                              List<Double> ratioList) {
        for (Account account : accountList.getAccountList()) {
            double ithWinLossRatio = account.calculateRatio();
            unsortedLeaderBoard.put(ithWinLossRatio, account.getUsername());
            ratioList.add(ithWinLossRatio);
        }
        ratioList.sort(Collections.reverseOrder());

        Map<String, Double>  sortedLeaderBoard = new LinkedHashMap<>();
        for (Double dbl : ratioList) {
            String username = unsortedLeaderBoard.get(dbl);
            unsortedLeaderBoard.remove(dbl, username);
            sortedLeaderBoard.put(username, dbl);
        }

        for (String username : sortedLeaderBoard.keySet()) {
            msgPrinter.printMessage(username + " = " + sortedLeaderBoard.get(username));
        }
    }
}
