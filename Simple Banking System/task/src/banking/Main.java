package banking;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        String url = "jdbc:sqlite:" + args[1];

        Database db = new Database(url);
        db.connect();
        UserInterface userInterface = new UserInterface(db);

        do {
            exitProgram = userInterface.mainMenu(scanner);
        } while (!exitProgram);

    }
}
