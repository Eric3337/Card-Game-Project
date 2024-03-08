package persistence;

import model.Account;
import model.Card;
import model.CardGame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// code is from JsonSpecializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads cardgame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CardGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCardGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private CardGame parseCardGame(JSONObject jsonObject) {
        Account accountSignedIn = (Account) jsonObject.get("accountSignedIn");
        CardGame cg = new CardGame(accountSignedIn, null);
        addPlayerCards(cg, jsonObject);
        addCompCards(cg, jsonObject);
        return cg;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addPlayerCards(CardGame cg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("playerCards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addPlayerCard(cg, nextCard);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPlayerCard(CardGame cg, JSONObject jsonObject) {
        int num = Integer.valueOf(jsonObject.getString("int"));
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
        int num = Integer.valueOf(jsonObject.getString("int"));
        String suit = jsonObject.getString("suit");
        Card card = new Card(num, suit);
        cg.addCompCard(card);
    }
}

