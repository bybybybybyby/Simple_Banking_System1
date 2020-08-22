package banking;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:" + args[1];

        Database db = new Database(url);
        UserInterface userInterface = new UserInterface(db);

        userInterface.mainMenu();
    }
}
