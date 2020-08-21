package banking;

import org.sqlite.SQLiteDataSource;

import javax.xml.transform.Result;
import java.sql.*;

public class Database {
    Connection connection;

    String url;
    int id;

    public int getId() {
        return id;
    }

    public Database(String url) {
        this.connection = connection;
        this.url = url;
        this.id = 1;
    }

    public void connect() {
//        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Exception1: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Exception2: " + ex.getMessage());
            }
        }
    }

    public void createNewTable() {

        // Drop old table if it exists
        String dropOld = "DROP TABLE IF EXISTS card;";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "id INTEGER, \n"
                + "number TEXT, \n"
                + "pin TEXT,\n"
                + "balance INTEGER DEFAULT 0"
                + ");";

        try (
                Connection connection = DriverManager.getConnection(url);
                Statement stmt = connection.createStatement()) {

            //drop old table
            stmt.execute(dropOld);

            //create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Exception3: " + e.getMessage());
        }
    }

    public void insert(int id, String number, String pin, int balance) {
        String sql = "INSERT INTO card(id,number,pin,balance) VALUES(?, ?, ?, ?)";

        try ( //connection = this.connect();
              Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, number);
            pstmt.setString(3, pin);
            pstmt.setInt(4, balance);
            pstmt.executeUpdate();
            this.id++;
        } catch (SQLException e) {
            System.out.println("Exception4: " + e.getMessage());
        }
    }


    //TODO: CHECK CREDENTIALS SEEMS TO FAIL ON TEST
    public boolean checkCredentials(String ccNum, String pinNum) {
        String sql = "SELECT pin FROM card WHERE number = " + ccNum;

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

//            System.out.println("RSGETSTRING=" + rs.getString("pin"));

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

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            balance = rs.getInt("balance");
        } catch (SQLException e) {
            System.out.println("Exception8: " + e.getMessage());
        }
        return balance;
    }

}
