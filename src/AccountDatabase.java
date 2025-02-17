/**
 * Manages a database of accounts.
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

    public AccountDatabase() {
        this.accounts = new Account[INITIAL_CAPACITY];
        this.size = 0;
        this.archive = new Archive();
    }

    private int find(Account account) {
        for (int i = 0; i < size; i++) {
            if (accounts[i] != null && accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private int find(AccountNumber accountNumber) {
        for (int i = 0; i < size; i++) {
            if (accounts[i] != null && accounts[i].getNumber().equals(accountNumber)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private void grow() {
        Account[] newAccounts = new Account[accounts.length + CAPACITY_INCREMENT];
        for (int i = 0; i < size; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    }

    public void add(Account account) {
        if (contains(account)) {
            return; // Don't add duplicates
        }
        if (size == accounts.length) {
            grow();
        }
        accounts[size++] = account;
    }

    public void removeAccount(AccountNumber accountNumber) {
        int index = find(accountNumber);
        if (index != NOT_FOUND) {
            archive.add(accounts[index]); // Archive before removing
            accounts[index] = accounts[--size];
            accounts[size] = null;
        }
    }

    public void removeAccount(Account account) {
        int index = find(account);
        if (index != NOT_FOUND) {
            archive.add(accounts[index]); // Archive before removing
            accounts[index] = accounts[--size];
            accounts[size] = null;
        }
    }

    public Account getAccount(AccountNumber accountNumber) {
        int index = find(accountNumber);
        if (index != NOT_FOUND) {
            return accounts[index];
        }
        return null;
    }

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

    public void deposit(AccountNumber number, double amount) {
        Account account = getAccount(number);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public void printArchive() {
        archive.print();
    }

    public void printByBranch() {}

    public void printByHolder() {}

    public void printByType() {}

    public void printAllAccounts() {
        System.out.println("All accounts in database:");
        for (int i = 0; i < size; i++) {
            System.out.println(accounts[i]);
        }
    }
}