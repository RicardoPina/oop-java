public class TestRunner {
    public static void main(String[] args) {
        testDate();
        testProfile();
        testAccountNumber();
        testAccount();
    }

    /**
     * Tests the Date class methods.
     */
    private static void testDate() {
        System.out.println("Testing Date class...");

        Date validDate1 = new Date(2024, 2, 29); // Leap year
        Date validDate2 = new Date(2023, 12, 31); // Valid end-of-year date
        Date invalidDate1 = new Date(2023, 2, 29); // Not a leap year
        Date invalidDate2 = new Date(2023, 4, 31); // April has only 30 days
        Date invalidDate3 = new Date(2023, 13, 10); // Invalid month
        Date invalidDate4 = new Date(2023, 0, 15); // Invalid month

        System.out.println(validDate1 + " is valid? " + validDate1.isValid()); // Expected: true
        System.out.println(validDate2 + " is valid? " + validDate2.isValid()); // Expected: true
        System.out.println(invalidDate1 + " is valid? " + invalidDate1.isValid()); // Expected: false
        System.out.println(invalidDate2 + " is valid? " + invalidDate2.isValid()); // Expected: false
        System.out.println(invalidDate3 + " is valid? " + invalidDate3.isValid()); // Expected: false
        System.out.println(invalidDate4 + " is valid? " + invalidDate4.isValid()); // Expected: false

        System.out.println();
    }

    /**
     * Tests the Profile class methods.
     */
    private static void testProfile() {
        System.out.println("Testing Profile class...");

        Date dob1 = new Date(2000, 5, 20);
        Date dob2 = new Date(1995, 10, 15);
        Date dob3 = new Date(2000, 5, 20); // Same as dob1

        Profile profile1 = new Profile("John", "Doe", dob1);
        Profile profile2 = new Profile("Alice", "Smith", dob2);
        Profile profile3 = new Profile("John", "Doe", dob3);

        System.out.println("Profile1 equals Profile2? " + profile1.equals(profile2)); // Expected: false
        System.out.println("Profile1 equals Profile3? " + profile1.equals(profile3)); // Expected: true

        System.out.println("Profile1 compared to Profile2: " + profile1.compareTo(profile2)); // Expected: -1
        System.out.println("Profile2 compared to Profile1: " + profile2.compareTo(profile1)); // Expected: 1
        System.out.println("Profile1 compared to Profile3: " + profile1.compareTo(profile3)); // Expected: 0

        System.out.println();
    }

    /**
     * Tests the AccountNumber class methods.
     */
    private static void testAccountNumber() {
        System.out.println("Testing AccountNumber class...");

        AccountNumber accNum1 = new AccountNumber(Branch.EDISON, AccountType.CHECKING);
        AccountNumber accNum2 = new AccountNumber(Branch.PISCATAWAY, AccountType.SAVINGS);
        AccountNumber accNum3 = new AccountNumber(Branch.EDISON, AccountType.CHECKING); // Same branch & type

        System.out.println("Account 1 Number: " + accNum1);
        System.out.println("Account 2 Number: " + accNum2);
        System.out.println("Account 1 equals Account 3? " + accNum1.equals(accNum3)); // Expected: false
        System.out.println("Comparison Account 1 vs Account 2: " + accNum1.compareTo(accNum2));

        System.out.println();
    }

    /**
     * Tests the Account class methods.
     */
    private static void testAccount() {
        System.out.println("Testing Account class...");

        AccountNumber accNum1 = new AccountNumber(Branch.EDISON, AccountType.CHECKING);
        Profile holder1 = new Profile("John", "Doe", new Date(2000, 5, 20));
        Account account1 = new Account(accNum1, holder1, 500.00);

        System.out.println("Initial Account: " + account1);

        account1.deposit(200);
        System.out.println("After deposit of 200: " + account1);

        account1.withdraw(100);
        System.out.println("After withdrawal of 100: " + account1);

        System.out.println("Trying to withdraw 1000 (exceeds balance)...");
        account1.withdraw(1000); // Should not be allowed
        System.out.println("Final Account: " + account1);

        System.out.println();
    }
}
