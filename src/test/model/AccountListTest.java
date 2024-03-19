package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountListTest {
    private AccountList testAccountList;
    private Account testAccount1;
    private Account testAccount2;
    private Account testAccount3;

    @BeforeEach
    void runBefore() {
        this.testAccountList = new AccountList();
        this.testAccount1 = new Account("User1", "123");
        this.testAccount2 = new Account("User2", "456");
        this.testAccount3 = new Account("User3", "789");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testAccountList.getAccountList().size());
    }

    @Test
    void testAddAccountOnce() {
        testAccountList.addAccount((testAccount1));
        assertEquals(1, testAccountList.getAccountList().size());
        assertEquals(testAccount1, testAccountList.getAccountList().get(0));
    }

    @Test
    void testAddAccountMultiple() {
        testAccountList.addAccount((testAccount1));
        assertEquals(testAccount1, testAccountList.getAccountList().get(0));
        testAccountList.addAccount((testAccount2));
        assertEquals(testAccount2, testAccountList.getAccountList().get(1));
        testAccountList.addAccount((testAccount3));
        assertEquals(3, testAccountList.getAccountList().size());
        assertEquals(testAccount3, testAccountList.getAccountList().get(2));
    }
}
