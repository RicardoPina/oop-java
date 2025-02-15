import java.util.Arrays;
import java.util.Scanner;

public class TransactionManager {
    // Default constructor
    public TransactionManager(){
    }

    enum Commands {
        O, C, D, W, P, PA, PB, PH, PT, Q
    }

//    public enum AccountType {
//        //    list the account types here
//        Savings, Savings2
//        }
    public void run() {
        System.out.println("System is up and running.");
        // start the scanner
        Scanner scanner = new Scanner(System.in);
        // initiate variable to store the input
        String input;

        // while the input is not q
        while(!(input = scanner.nextLine()).equalsIgnoreCase("Q")) {
            // tokenize input and store in "tokens"
            String[] tokens = input.split(" +");
            // if there is an input, call processCommand()
            if (!input.trim().isEmpty()) {
                this.processCommand(tokens);
            }
        }
        System.out.println("Collection Manager terminated.");
    }

    private void processCommand(String[] tokens){
        String input = tokens[0].trim();
        Commands command = null;
        try {
        command = Commands.valueOf(input);
        System.out.println("Command: " +command);
        // implement the switch cases

            switch (command) {
                case O:
                    System.out.println("Selected: Open an account");
                    openAccount(tokens);
                    break;
                case C:
                    System.out.println("Selected: Close existing account");
                    closeAccount(tokens);
                    break;
                case D:
                    depositProcess(tokens);
                    System.out.println("Selected: Deposit to account");
                    break;
                case W:
                    withdrawProcess(tokens);
                    System.out.println("Selected: Withdraw from account");
                    break;
                case P:
                    System.out.println("Selected: Print account database");
                    break;
                case PA:
                    AccountDatabase.printArchive();
                    System.out.println("Selected: Print archived of closed accounts");
                    break;
                case PB:
                    AccountDatabase.printByBranch();
                    System.out.println("Selected: Print branch-phabetical accounts");
                    break;
                case PH:
                    AccountDatabase.printByHolder();
                    System.out.println("Selected: Print ordered by the account holder’s profile");
                    break;
                case PT:
                    AccountDatabase.printByType();
                    System.out.println("Selected: Print ordered by account type");
                    break;
                case Q:
                    System.out.println("Transaction Manager is terminated\n");
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }catch (IllegalArgumentException e){
            System.out.println("Invalid command: " + input);
        }
    }

private void openAccount(String[] tokens){
    //The token might be in lowercase, upper case or any combination.
    String accountType = tokens[1].trim().toLowerCase();
    String branchType = tokens[2].trim().toLowerCase();
    String firstName = tokens[3].trim();
    String lastName = tokens[4].trim();
    String dob = tokens[5].trim();

    AccountType type = AccountType.valueOf(accountType);
    Branch branch = Branch.valueOf(branchType);
    ////////////////////////////////////////////////////////
    Date date = new Date(dob);
    Profile newPerson = new Profile(lastName, firstName, date);
    AccountNumber accountNumber = new AccountNumber(type, branch);
    Account newAccount = new Account(accountNumber, newPerson);
    add(newAccount);
}

private void closeAccount(String[] tokens) {
    if (tokens.length < 2) {
        System.out.println("Missing data for closing account!");
        return;
    }
    // CASE 1:
    if (tokens.length == 2) {
        String accountNumberString = tokens[1].trim();
        int accountNum = Integer.parseInt(accountNumberString);
        //OVERLOAD THE METHODS TO MAKE IT WORK
        Account accountToClose = AccountDatabase.find(accountNum);

        if (accountToClose == null) {
            System.out.println("Account not found.");
            return;
        }
        //OVERLOAD THE METHODS TO MAKE IT WORK
        AccountDatabase.remove(accountToClose);  // which should also archive it

        boolean success = database.removeByNumber(accountNumberString);

        if (success) System.out.println("Account [" + accountNumberString + "] closed.");
    }

    // CASE 2: "C fName lName dob"
    if (tokens.length == 4) {
        String firstName = tokens[1].trim();
        String lastName = tokens[2].trim();
        String dobString = tokens[3].trim();

        Date dob = new Date(dobString);
        Profile profile = new Profile(lastName, firstName, dob);

        /*
         Now find ALL accounts belonging to that profile.
         Typically, you'd have a method in your database that returns a list of matching accounts:
         List<Account> accountsForProfile = database.findAll(profile);
         Then, for each account found, remove it and archive it.
         Example pseudo-code:
         if (accountsForProfile.isEmpty()) {
            System.out.println("No accounts found for " + firstName + " " + lastName + ".");
         } else {
            for (Account acct : accountsForProfile) {
                database.remove(acct);
            }
            System.out.println("All accounts for " + firstName + " " + lastName + " have been closed.");
         }
        */

    }
}

private void withdrawProcess(String[] tokens){}

private void depositProcess(String[] tokens){}
}

