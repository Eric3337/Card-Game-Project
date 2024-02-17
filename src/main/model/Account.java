package model;

// Account represents the account of any users that wish to create one. The account includes
//          unique id, a username, a password, the number of games won and lost and played in total.
//          the account can also show the win loss ratio which is used to calculate the account's
//          position on the leaderboard
public class Account {
    private static int nextAccId = 1;
    private int id;
    private String username;
    private String pw;
    private Integer gamesWon;
    private Integer gamesLost;
    private Integer totalGamesPlayed;

    // MODIFIES: this
    // EFFECTS: creates an account with a unique id, username, password and sets
    //          games won, lost, and total games played to zero
    public Account(String accName, String password) {
        id = nextAccId++;
        username = accName;
        pw = password;
        gamesWon = 0;
        gamesLost = 0;
        totalGamesPlayed = 0;
    }

    // EFFECTS: calculates the win loss ratio
    public double calculateRatio() {
        if (gamesLost == 0 && gamesWon == 1) {
            return 1;
        } else if (gamesLost == 0) {
            return gamesWon / 1;
        } else {
            return gamesWon / gamesLost;
        }
    }

    public void wonAGame() {
        gamesWon++;
    }

    public void lostAGame() {
        gamesLost++;
    }

    public void playedAGame() {
        totalGamesPlayed++;
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
