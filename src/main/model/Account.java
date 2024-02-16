package model;

public class Account {
    private static int nextAccId = 1;
    private int id;
    private String username;
    private String pw;
    private Integer gamesWon;
    private Integer gamesLost;
    private Integer totalGamesPlayed;

    public Account(String accName, String password) {
        id = nextAccId++;
        username = accName;
        pw = password;
        gamesWon = 0;
        gamesLost = 0;
        totalGamesPlayed = 0;
    }

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

    public int getId() {
        return id;
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
