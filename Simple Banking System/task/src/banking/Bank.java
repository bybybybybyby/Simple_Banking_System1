package banking;

public class Bank {
    private Database db;

    public Bank(Database db) {
        this.db = db;
        db.createNewTable();
    }

    public void createAccount() {
        Account newAccount = new Account(db);
    }

    public Database getDb() {
        return db;
    }
}
