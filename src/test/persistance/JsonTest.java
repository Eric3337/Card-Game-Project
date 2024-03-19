package persistance;

import model.Account;
import model.AccountList;
import model.Card;
import model.CardGame;
import ui.CardApp;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAccount(int id, String user, String pw, int gamesWon, int gamesLost, int total, Account acc) {
        assertEquals(id, acc.getId());
        assertEquals(user, acc.getUsername());
        assertEquals(pw, acc.getPw());
        assertEquals(gamesWon, acc.getGamesWon());
        assertEquals(gamesLost, acc.getGamesLost());
        assertEquals(total, acc.getTotalGamesPlayed());
    }

    protected void checkCardGame(Account accountSignedIn, List<Card> playerCards,
                                 List<Card> compCards, CardApp cardApp, CardGame cardGame) {
        assertEquals(accountSignedIn, cardGame.getAccountSignedIn());
        assertEquals(playerCards, cardGame.getPlayerCards());
        assertEquals(compCards, cardGame.getCompCards());
        assertEquals(cardApp, cardGame.getCardApp());
    }
}
