package persistance;

import model.Account;
import model.AccountList;
import model.CardGame;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountList al = reader.readAccountList(null);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountList.json");
        try {
            AccountList al = reader.readAccountList(null);
            assertEquals(0, al.getAccountList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountList.json");
        try {
            AccountList al = reader.readAccountList(null);
            assertEquals(2, al.getAccountList().size());
            List<Account> accounts = al.getAccountList();
            checkAccount(0, "Eric", "123", 1, 2, 3, accounts.get(0));
            checkAccount(1, "eric", "456", 3, 1, 4, accounts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
