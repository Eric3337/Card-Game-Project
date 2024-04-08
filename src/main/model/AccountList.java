package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;

// Represents the list of accounts for the game application
public class AccountList implements Writable {
    private List<Account> accountList;
    private EventLog eventLog;

    // EFFECTS: constructs an empty list of accounts
    public AccountList() {
        this.accountList = new ArrayList<>();
        this.eventLog = EventLog.getInstance();
    }

    // EFFECTS: returns current list of accounts
    public List<Account> getAccountList() {
        return accountList;
    }

    // EFFECTS: adds in accounts to list of accounts
    public void addAccount(Account account) {
        this.accountList.add(account);
        eventLog.logEvent(new Event("Added user: " + account.getUsername()
                + " to list of accounts."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accountList", accountList);
        return json;
    }
}
