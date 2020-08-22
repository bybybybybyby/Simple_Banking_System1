package banking;

import java.util.Random;

public class Account {

    private static final String ISSUER_ID = "400000";
    private static int currentCreditCardNumber = 1;
    private String ccNum;
    private String pinNum;
    private double balance;

    public Account(Database db) {
        Random random = new Random();
        createCreditCard(random);
        createPin(random);
        this.balance = 0.0;

        db.insert(ccNum, pinNum, 0);

        System.out.println("Your card has been created\n" +
                "Your card number: \n" +
                ccNum + "\n" +
                "Your card PIN:\n" +
                pinNum + "\n");
    }

    // Create new credit card
    private void createCreditCard(Random random) {
        String accountId = String.format("%09d", currentCreditCardNumber);
        String issuerAndAccountId = ISSUER_ID + accountId;

        String checksum = calculateCheckSum(issuerAndAccountId);

        this.ccNum = issuerAndAccountId + checksum;
        currentCreditCardNumber++;
    }

    /**
     * Calculate credit card checksum, which will be the last digit.
     * Final resulting number will pass the Luhn algorithm
     * Steps are:
     * Take the first 15 digits.
     * Multiply odd digits (first, third, fifth, ... fifteenth) by 2.
     * Subtract 9 to numbers over 9.
     * Add all the numbers.
     * Final digit must make total sum a multiple of 10.
     *
     * @param issuerAndAccountId    the first 15 digits of number
     * @return  the 16 digit Credit Card number that passes Luhns algorithm
     */
    private String calculateCheckSum(String issuerAndAccountId) {
        // long number = Long.valueOf(issuerAndAccountId);
        String[] digits = issuerAndAccountId.split("");
        // multiplying odd digits
        for (int i = 0; i < issuerAndAccountId.length(); i += 2) {
            digits[i] = Integer.toString(Integer.parseInt(digits[i]) * 2);
        }
        // subtract 9 if value over 9
        for (int i = 0; i < issuerAndAccountId.length(); i++) {
            if (Integer.parseInt(digits[i]) > 9) {
                digits[i] = Integer.toString(Integer.parseInt(digits[i]) - 9);
            }
        }
        // add all numbers
        int sum = 0;
        for (String s : digits) {
            sum += Integer.parseInt(s);
        }
        // calculate last digit so sum is a multiple of 10
        int checksum = 10 - sum % 10;
        if (checksum == 10) {
            checksum = 0;
        }
        return String.valueOf(checksum);
    }

    // Create 4 digit PIN
    private void createPin(Random random) {
        this.pinNum = String.format("%04d", random.nextInt(10_000));
    }

    // Check if PIN matches
    public boolean checkCredentials(String pinInput) {
        return pinInput.equals(pinNum);
    }
}
