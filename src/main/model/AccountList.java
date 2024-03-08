package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;

// Represents the list of accounts for the game application
public class AccountList {
    private List<Account> accountList;

    // EFFECTS: constructs an empty list of accounts
    public AccountList() {
        accountList = new ArrayList<>();
    }

    // EFFECTS: returns current list of accounts
    public List<Account> getAccountList() {
        return accountList;
    }

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("name", name);
//        json.put("thingies", thingiesToJson());
//        return json;
//    }

}
