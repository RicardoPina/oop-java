/**
 * Represents an archive of closed accounts using a linked list.
 * @author [Your Name(s) Here]
 */
public class Archive {
    private AccountNode first; // the head node of the linked list

    private static class AccountNode { // Inner class for the node
        Account account;
        AccountNode next;

        AccountNode(Account account) {
            this.account = account;
            this.next = null;
        }
    }

    public void add(Account account) {
        AccountNode newNode = new AccountNode(account);
        newNode.next = first; // Insert at the front
        first = newNode;
    }

    public void print() {
        if (first == null) {
            System.out.println("Archive is empty.");
            return; // Important: Return early if the archive is empty
        }

        AccountNode current = first;
        System.out.println("Closed Accounts:"); // Header for the output

        while (current != null) {
            System.out.println(current.account);  // Print the account details. toString() method of account will be used.
            current = current.next;
        }
    }

    public static void main(String[] args) {
        AccountDatabase database = new AccountDatabase(); // Get the AccountDatabase instance
        //Archive archive = new Archive(); // You can still create an Archive instance for testing, but it's not strictly necessary for this test

        // Sample Account objects (replace with your actual Account creation)
        Account acc1 = new Account(new AccountNumber(Branch.EDISON, AccountType.CHECKING), new Profile("Doe", "John", new Date(2000, 1, 1)), 100);
        Account acc2 = new Account(new AccountNumber(Branch.BRIDGEWATER, AccountType.SAVINGS), new Profile("Smith", "Jane", new Date(1995, 5, 10)), 200);
        Account acc3 = new Account(new AccountNumber(Branch.PISCATAWAY, AccountType.SAVINGS), new Profile("Lee", "David", new Date(1998, 8, 15)), 300);

        // Add accounts to the database FIRST
        database.add(acc1);
        database.add(acc2);
        database.add(acc3);

        // Now, remove them from the database. This will automatically add them to the archive.
        database.removeAccount(acc1.getNumber());
        database.removeAccount(acc2);
        database.removeAccount(acc3);

        database.printArchive(); // Print the archive through the database

        // Test case for an empty archive (you can test this by removing all accounts and then printing)
        Account acc4 = new Account(new AccountNumber(Branch.EDISON, AccountType.CHECKING), new Profile("Test", "Account", new Date(2001, 1, 1)), 100);
        database.add(acc4);
        database.removeAccount(acc4.getNumber());

    }
}