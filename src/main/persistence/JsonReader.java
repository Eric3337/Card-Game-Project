package persistence;

import model.Account;
import model.Card;
import model.CardGame;
import model.AccountList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;
import ui.CardApp;

// Represents a reader that reads workroom from JSON data stored in file
// code is from JsonSpecializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads AccountList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountList readAccountList(CardApp cardApp) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountList(jsonObject, cardApp);
    }

    private AccountList parseAccountList(JSONObject jsonObject, CardApp cardApp) {
        AccountList accountList = addAccounts(cardApp, jsonObject);
        return accountList;
    }

    // MODIFIES: cardApp
    // EFFECTS: parses nextAccount from JSON object and adds them to accountList
    private AccountList addAccounts(CardApp cardApp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accountList");
        AccountList accountList = new AccountList();
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            accountList.addAccount(jsonToAccount(cardApp, nextAccount));
        }
        return accountList;
    }

    private Account jsonToAccount(CardApp cardApp, JSONObject jsonObject) {
        int gamesLost = (int) jsonObject.get("gamesLost");
        String pw = (String) jsonObject.get("pw");
        int totalGamesPlayed = (int) jsonObject.get("totalGamesPlayed");
        String username = (String) jsonObject.get("username");
        int gamesWon = (int) jsonObject.get("gamesWon");

        Account account = new Account(username, pw, gamesWon, gamesLost, totalGamesPlayed);
        return account;
    }

    // EFFECTS: reads cardgame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CardGame readCardGame(AccountList accountList, CardApp cardApp) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCardGame(jsonObject, accountList, cardApp);
    }

    // EFFECTS: parses card game from JSON object and returns it
    private CardGame parseCardGame(JSONObject jsonObject, AccountList accountList, CardApp cardApp) {
        String savedAccountUsername = (String) jsonObject.get("accountSignedInUsername");
        Account accountSignedIn = null;
        for (Account a : accountList.getAccountList()) {
            if (a.getUsername() == savedAccountUsername) {
                accountSignedIn = a;
                break;
            }
        }
        CardGame cg = new CardGame(accountSignedIn, cardApp);
        addPlayerCards(cg, jsonObject);
        addCompCards(cg, jsonObject);
        return cg;
    }

    // MODIFIES: wr
    // EFFECTS: parses nextCard from JSON object and adds them to playerCards
    private void addPlayerCards(CardGame cg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("playerCards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addPlayerCard(cg, nextCard);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses card from JSON object and adds it to playerCards
    private void addPlayerCard(CardGame cg, JSONObject jsonObject) {
        int num = (int) jsonObject.get("int");
        String suit = jsonObject.getString("suit");
        Card card = new Card(num, suit);
        cg.addPlayerCard(card);
    }

    private void addCompCards(CardGame cg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("compCards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCompCard(cg, nextCard);
        }
    }

    private void addCompCard(CardGame cg, JSONObject jsonObject) {
        int num = (int) jsonObject.get("int");
        String suit = jsonObject.getString("suit");
        Card card = new Card(num, suit);
        cg.addCompCard(card);
    }
}

