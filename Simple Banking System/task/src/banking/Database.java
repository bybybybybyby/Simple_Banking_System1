package banking;

import java.sql.*;

public class Database {

    String url;

    public Database(String url) {
        this.url = url;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Exception1: " + e.getMessage());
        }
        return connection;
    }

    public void createNewTable() {

        // Drop old table if it exists
        String dropOld = "DROP TABLE IF EXISTS card;";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + "number TEXT, \n"
                + "pin TEXT,\n"
                + "balance INTEGER DEFAULT 0"
                + ");";

        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {

            //drop old table
            stmt.execute(dropOld);

            //create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Exception3: " + e.getMessage());
        }
    }

    public void insert(String number, String pin, int balance) {
        String sql = "INSERT INTO card(number,pin,balance) VALUES(?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception4: " + e.getMessage());
        }
    }

    public boolean checkCredentials(String ccNum, String pinNum) {
        String sql = "SELECT pin FROM card WHERE number = " + ccNum;

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            if (pinNum.equals(rs.getString("pin"))) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Exception7: " + e.getMessage());
        }
        return false;
    }

    public int getBalance(String ccNum) {
        int balance = -1;
        String sql = "SELECT balance FROM card WHERE number = " + ccNum;

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            balance = rs.getInt("balance");
        } catch (SQLException e) {
            System.out.println("Exception8: " + e.getMessage());
        }
        return balance;
    }

    public void addIncome(String cardNum, int income) {
        String sql = "UPDATE card " +
                "SET balance = balance + " + income + " " +
                "WHERE number = " + cardNum;
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception_AddIncome: " + e.getMessage());
        }
    }

    public void closeAccount(String cardNum) {
        String sql = "DELETE FROM card WHERE number = " + cardNum;
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.printf("SQLException_closeAccount: " + e.getMessage());
        }

    }

}
