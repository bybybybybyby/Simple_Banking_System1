package banking;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Account> accounts;
    private Database db;

    public Bank(Database db) {
        accounts = new ArrayList<>();
        this.db = db;
        db.createNewTable();
    }

    public void createAccount() {
        Account newAccount = new Account(db);
        accounts.add(newAccount);

    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Database getDb() {
        return db;
    }
}
