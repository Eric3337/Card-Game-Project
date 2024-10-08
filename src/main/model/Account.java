package model;

import org.json.JSONObject;
import persistence.Writable;

// Account represents the account of any users that wish to create one. The account includes
//          unique id, a username, a password, the number of games won and lost and played in total.
//          the account can also show the win loss ratio which is used to calculate the account's
//          position on the leaderboard
public class Account {
    private String username;
    private String pw;
    private Integer gamesWon;
    private Integer gamesLost;
    private Integer totalGamesPlayed;

    private EventLog eventLog;

    // MODIFIES: this
    // EFFECTS: creates an account with a unique id, username, password and sets
    //          games won, lost, and total games played to zero
    public Account(String accName, String password) {
        username = accName;
        pw = password;
        gamesWon = 0;
        gamesLost = 0;
        totalGamesPlayed = 0;
        this.eventLog = EventLog.getInstance();
    }

    public Account(String username, String pw, Integer gamesWon, Integer gamesLost, Integer totalGamesPlayed) {
        this.username = username;
        this.pw = pw;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.totalGamesPlayed = totalGamesPlayed;
        this.eventLog = EventLog.getInstance();
    }

    // EFFECTS: calculates the win loss ratio, results is a double type
    public double calculateRatio() {
        if (gamesLost == 0 && gamesWon == 1) {
            return 1;
        } else if (gamesLost == 0) {
            return (double)gamesWon / 1;
        } else {
            return (double)gamesWon / gamesLost;
        }
    }

    public void wonAGame() {
        gamesWon++;
        eventLog.logEvent(new Event(getUsername() + " has won a game."));
    }

    public void lostAGame() {
        gamesLost++;
        eventLog.logEvent(new Event(getUsername() + " has lost a game."));
    }

    public void playedAGame() {
        totalGamesPlayed++;
        eventLog.logEvent(new Event(getUsername() + " has played a game."));
    }

    public String getUsername() {
        return username;
    }

    public String getPw() {
        return pw;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public Integer getGamesLost() {
        return gamesLost;
    }

    public Integer getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
}
