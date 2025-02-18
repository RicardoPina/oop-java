/**
 * Manages a database of accounts.
 * Provides methods to add, remove, find, and print
 * Also manages an archive of closed accounts.
 *
 * @author Ricardo Pina
 */
public class AccountDatabase {
    private Account[] accounts;
    private int size;
    private Archive archive; // a linked list of closed accounts

    private static final int NOT_FOUND = -1;
    private static final int INITIAL_CAPACITY = 4;
    private static final int CAPACITY_INCREMENT = 4;

    /**
     * Constructor for the AccountDatabase class.
     * Initializes the accounts array with an initial capacity.
     */
    public AccountDatabase() {
        this.accounts = new Account[INITIAL_CAPACITY];
        this.size = 0;
        this.archive = new Archive();
    }

    /**
     * Finds the index of an account obj in the accounts array.
     *
     * @param account the account to find
     * @return the index of the account in the array, or NOT_FOUND if not found
     */
    private int find(Account account) {
        for (int i = 0; i < size; i++) {
            if (accounts[i] != null && accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    /**
     * Finds the index of an account number in the accounts array.
     *
     * @param accountNumber the account number to find
     * @return the index of the account in the array, or NOT_FOUND if not found
     */
    private int find(AccountNumber accountNumber) {
        for (int i = 0; i < size; i++) {
            if (accounts[i] != null && accounts[i].getNumber().equals(accountNumber)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grows the accounts array by the CAPACITY_INCREMENT.
     */ 
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + CAPACITY_INCREMENT];
        for (int i = 0; i < size; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    /**
     * Checks if the database contains a given account number.
     *
     * @param accountNumber the account number to find
     * @return true if the account number is found, false otherwise
     */
    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    }
    /**
     * Adds a new account to the database.
     * If the account number is already in the database, it will not be added.
     *
     * @param accountNumber the account number to find
     * 
     */
    public void add(Account account) {
        if (contains(account)) {
            return; // Don't add duplicates
        }
        if (size == accounts.length) {
            grow();
        }
        accounts[size++] = account;
    }

    /**
     * Removes an account from the database.
     *
     * @param accountNumber the account number to remove
     */
    public void removeAccount(AccountNumber accountNumber) {
        int index = find(accountNumber);
        if (index != NOT_FOUND) {
            archive.add(accounts[index]); // Archive before removing
            accounts[index] = accounts[--size];
            accounts[size] = null;
        }
    }
    /**
     * Removes an account from the database.
     *
     * @param account the account to remove
     */
    public void removeAccount(Account account) {
        int index = find(account);
        if (index != NOT_FOUND) {
            archive.add(accounts[index]); // Archive before removing
            accounts[index] = accounts[--size];
            accounts[size] = null;
        }
    }
    /*
     * Returns the account with the given account number.
     */
    public Account getAccount(AccountNumber accountNumber) {
        int index = find(accountNumber);
        if (index != NOT_FOUND) {
            return accounts[index];
        }
        return null;
    }

    /**
     * Returns the accounts associated to a given profile.
     *
     * @param profile the account number to find
     * @return an array of accounts associated to the profile or an empty array if no matches are found
     */

    public Account[] findAccountsByProfile(Profile profile) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            if (account.getHolder() != null && account.getHolder().equals(profile)) {
                count++;
            }
        }

        if (count == 0) {
            return new Account[0]; // Return an empty array if no matches are found
        }

        Account[] matchingAccounts = new Account[count];
        int index = 0;
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            if (account != null && account.getHolder() != null && account.getHolder().equals(profile)) {
                matchingAccounts[index++] = account;
            }
        }
        return matchingAccounts;
    }

    /**
     * Withdraws a given amount from an account.
     * If the account is a money market account and the balance falls below $2000 
     * the account is downgraded to a savings account.
     *
     * @param accountNumber the account number to withdraw from
     * @param amount the amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    public boolean withdraw(AccountNumber number, double amount) {
    Account account = getAccount(number);
    if (account != null) {
        double initialBalance = account.getBalance();
        try {
            account.withdraw(amount);
            double finalBalance = account.getBalance();
            if (initialBalance != finalBalance) {
                if (account.getNumber().getType() == AccountType.MONEYMARKET && finalBalance < 2000) {
                    account.downgradeAccountType();
                }
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    return false;
}

    /**
     * Deposits a specified amount into an account.
     *
     * @param number The account number of the account to deposit into.
     * @param amount The amount to deposit.
     */

    public void deposit(AccountNumber number, double amount) {
        Account account = getAccount(number);
        if (account != null) {
            account.deposit(amount);
        }
    }

    /**
     * Prints the archive of closed accounts.
     * If the database is empty, prints a message indicating that.
     */
    public void printArchive() {
        if (size == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        archive.print();
    }
    /**
     * Prints all accounts in the database, ordered by branch location (county, city).
     * If the database is empty, prints a message indicating that.
     */
    public void printByBranch() {
        if (size == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println("\n" + "*List of accounts ordered by branch location (county, city).");
        String[] counties = {"Mercer", "Middlesex", "Somerset"};
        String[][] branches = {
            {"PRINCETON"},
            {"EDISON", "PISCATAWAY"},
            {"BRIDGEWATER", "WARREN"}
        };

        // Iterate through each county and branch to print accounts
        for (int i = 0; i < counties.length; i++) {
            String county = counties[i];
            boolean countyPrinted = false;

            for (String branch : branches[i]) {
                for (int j = 0; j < size; j++) {
                    Account account = accounts[j];
                    if (account != null && account.getNumber().getBranch().name().equals(branch)) {
                        if (!countyPrinted) {
                            System.out.println("County: " + county);
                            countyPrinted = true;
                        }
                        System.out.println(account);
                    }
                }
            }
    }

    System.out.println("*end of list.\n");

    }
    /**
     * Prints all accounts in the database, ordered by account holder and account number.
     */
    public void printByHolder() {
        System.out.println("*List of accounts ordered by account holder and number.");

    // Bubble sort accounts by holder's profile and account number
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            Account a1 = accounts[j];
            Account a2 = accounts[j + 1];
            if (a1 != null && a2 != null) {
                int comparison = a1.getHolder().compareTo(a2.getHolder());
                if (comparison > 0 || (comparison == 0 && a1.getNumber().compareTo(a2.getNumber()) > 0)) {
                    // Swap accounts[j] and accounts[j + 1]
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }

    // Print sorted accounts
    for (int i = 0; i < size; i++) {
        if (accounts[i] != null) {
            System.out.println(accounts[i]);
        }
    }

    System.out.println("*end of list.");

    }
    /**
     * Prints all accounts in the database, ordered by account type and account number.
     * If the database is empty, prints a message indicating that.
     */
    public void printByType() {
        if (size == 0) {
            System.out.println("Account database is empty!");
            return;
        }
        System.out.println("\n*List of accounts ordered by account type and number.");

        // Bubble sort accounts by account type and account number
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                Account a1 = accounts[j];
                Account a2 = accounts[j + 1];
                if (a1 != null && a2 != null) {
                    int comparison = a1.getNumber().getType().compareTo(a2.getNumber().getType());
                    if (comparison > 0 || (comparison == 0 && a1.getNumber().compareTo(a2.getNumber()) > 0)) {
                        // Swap accounts[j] and accounts[j + 1]
                        Account temp = accounts[j];
                        accounts[j] = accounts[j + 1];
                        accounts[j + 1] = temp;
                    }
                }
            }
        }

        // Print sorted accounts by type
        AccountType currentType = null;
        for (int i = 0; i < size; i++) {
            if (accounts[i] != null) {
                AccountType accountType = accounts[i].getNumber().getType();
                if (currentType == null || !currentType.equals(accountType)) {
                    currentType = accountType;
                    System.out.println("Account Type: " + currentType.name());
                }
                System.out.println(accounts[i]);
            }
        }

        System.out.println("*end of list.");

    }
    /**
     * Prints all accounts in the database.
     * If the database is empty, prints a message indicating that.
     */
    public void printAllAccounts() {
        if (size == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println("\n*List of accounts in the account database.");
        for (int i = 0; i < size; i++) {
            System.out.println(accounts[i]);
        }
        System.out.println("*end of list.\n");
    }
    /**
     * Checks if a profile already has an account of a specific type.
     *
     * @param profile The profile to check.
     * @param type The account type to check.
     * @return True if the profile has an account of the specified type, false otherwise.
     */
    public boolean hasAccountOfType(Profile profile, AccountType type) {
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            if (account != null && account.getHolder().equals(profile) && account.getNumber().getType() == type) {
                return true;
            }
        }
        return false;
    }
}