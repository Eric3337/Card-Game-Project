package persistance;

import model.Account;
import model.AccountList;
import model.CardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private Account accountSignedIn;

    @BeforeEach
    void runBefore() {
        accountSignedIn = new Account("User", "123");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            CardGame cg = new CardGame(accountSignedIn, null);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountList() {
        try {
            AccountList al = new AccountList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.writeAccList(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            al = reader.readAccountList(null);
            assertEquals(0, al.getAccountList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountList() {
        try {
            AccountList al = new AccountList();
            al.addAccount(new Account("User1", "123"));
            al.addAccount(new Account("User2", "456"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.writeAccList(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            al = reader.readAccountList(null);
            List<Account> accounts = al.getAccountList();
            assertEquals(2, accounts.size());
            checkAccount(1, "User1", "123", 0, 0, 0, accounts.get(0));
            checkAccount(2, "User2", "456", 0, 0, 0, accounts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
