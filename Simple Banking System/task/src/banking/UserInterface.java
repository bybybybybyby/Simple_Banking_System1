package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {

    Bank bank;

    public UserInterface(Database db) {
        bank = new Bank(db);
    }


    // Top level menu
    public boolean mainMenu(Scanner scanner) {
        boolean exit = false;

        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");

        String input = scanner.nextLine();
        System.out.println();

        if ("1".equals(input)) {
            bank.createAccount();
            return false;
        } else if ("2".equals(input)) {
            exit = logIn(scanner);
//            return false;
        } else if ("0".equals(input)) {
            System.out.println("Bye!");
            exit = true;
        }
        return exit;
    }

    // Menu to log into account
    public boolean logIn(Scanner scanner) {
        boolean exit = false;
        boolean cardNumExists = false;
        boolean pinCorrect = false;
        System.out.println("Enter your card number:");
        String cardNum = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pinNum = scanner.nextLine();


        // For Database
        // Check to see if Cc number exists, then check if PIN matches.
//        ResultSet rs = bank.getDb().creditCardInfo(cardNum);
        if (this.bank.getDb().checkCredentials(cardNum, pinNum)) {
            System.out.println("\nYou have successfully logged in!\n");
            exit = loggedInMenu(scanner, cardNum);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
        return exit;
    }


    public boolean loggedInMenu(Scanner scanner, String cardNum) {
        boolean exit = false;
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");
            String input = scanner.nextLine();
            if ("1".equals(input)) {
                System.out.println("\nBalance: " + bank.getDb().getBalance(cardNum) );
            } else if ("2".equals(input)) {
                System.out.println("\nYou have successfully logged out!\n");
                loggedIn = false;
//                return;
            } else if ("0".equals(input)) {
                loggedIn = false;
                exit = true;
            }
        }
        return exit;
    }


}
