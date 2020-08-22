package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {

    Bank bank;
    Scanner scanner;

    public UserInterface(Database db) {
        bank = new Bank(db);
        scanner = new Scanner(System.in);
    }

    // Top level menu
    public void mainMenu() {
        while (true) {
            String input = ask("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            System.out.println();

            if ("1".equals(input)) {
                bank.createAccount();
            } else if ("2".equals(input)) {
                logIn();
            } else if ("0".equals(input)) {
                System.out.println("Bye!");
                break;
            }
        }
    }

    public static String ask(String s) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(s);
        return scanner.nextLine();
    }

    // Menu to log into account
    public void logIn() {
        String cardNum = ask("Enter your card number: ");
        String pinNum = ask("Enter your PIN: ");

        // For Database
        // Check to see if Cc number exists, then check if PIN matches.
        if (this.bank.getDb().checkCredentials(cardNum, pinNum)) {
            System.out.println("\nYou have successfully logged in!\n");
            loggedInMenu(cardNum);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }


    public void loggedInMenu(String cardNum) {
        boolean loggedIn = true;
        while (loggedIn) {
            String input = ask("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            if ("1".equals(input)) {
                System.out.println("\nBalance: " + bank.getDb().getBalance(cardNum));
            } else if ("2".equals(input)) {
                String income = UserInterface.ask("Enter income: ");
                bank.getDb().addIncome(cardNum, Integer.parseInt(income));
            } else if ("4".equals(input)) {
                System.out.println("\nThe account has been closed!\n");
                bank.getDb().closeAccount(cardNum);
            } else if ("5".equals(input)) {
                System.out.println("\nYou have successfully logged out!\n");
                loggedIn = false;
            } else if ("0".equals(input)) {
                loggedIn = false;
                System.exit(0);

            }
        }
    }




}
