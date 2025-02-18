import java.util.Scanner;

/**
 * Manages transactions for a bank, including opening and closing accounts,
 * depositing and withdrawing funds, and printing account information.
 * 
 * @author Ricardo Pina
 */
public class TransactionManager {
    private final AccountDatabase database = new AccountDatabase();
    /**
     * Default constructor for {@code TransactionManager}.
     */
    public TransactionManager(){
    }
    /**
     * Enum representing the valid commands that can be processed.
     */
    enum Commands {
        O, C, D, W, P, PA, PB, PH, PT, Q
    }
    /**
     * Runs the transaction manager, continuously prompting the user for
     * commands until the 'Q' command is entered.
     */
    public void run() {
        System.out.println("Transaction Manager is running.");
        // start the scanner
        Scanner scanner = new Scanner(System.in);
        // initiate variable to store the input
        String input;
        // while the input is not q
        while(!(input = scanner.nextLine()).equals("Q")) {
            // tokenize input and store in "tokens"
            String[] tokens = input.split(" +");
            // if there is an input, call processCommand()
            if (!input.trim().isEmpty()) {
                this.processCommand(tokens);
            }
        }
        System.out.println("Transaction Manager terminated.");
        scanner.close();
    }
    /*
     * Processes the command based on the input.
     * @param tokens An array of strings containing the command and its arguments.
     * 
     */
    private void processCommand(String[] tokens){
        String input = tokens[0].trim();
        Commands command;
        try {
            command = Commands.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command!");
            return;
        }
        // implement the switch cases
            switch (command) {
                case O:
                    openAccount(tokens);
                    break;
                case C:

                    closeAccount(tokens);
                    break;
                case D:
                    depositProcess(tokens);

                    break;
                case W:
                    withdrawProcess(tokens);

                    break;
                case P:
                    database.printAllAccounts();
                    break;
                case PA:
                    database.printArchive();
                    break;
                case PB:
                    database.printByBranch();
                    break;
                case PH:
                    database.printByHolder();
                    break;
                case PT:
                    database.printByType();
                    break;
                case Q:
                    System.out.println("Transaction Manager is terminated\n");
                    break;
                default:
                    System.out.println("Invalid command!");
            }
    }

/**
 * Opens an account after processing input.
 *
 * @param tokens An array of strings containing the account details.
 */
private void openAccount(String[] tokens){
    String accountType = tokens[1].trim().toUpperCase();
    String branchType = tokens[2].trim().toUpperCase();
    String firstName = tokens[3].trim();
    String lastName = tokens[4].trim();
    String dob = tokens[5].trim();
    String amount = tokens[6].trim();
    double depositAmount;
    AccountType type;
    Branch branch;
    Date date;
    // Check account type
    try {
        type = AccountType.valueOf(accountType);
    } catch (IllegalArgumentException e) {
        System.out.println(accountType.toLowerCase() + " - invalid account type.");
        return;
    }
    // Check branch
    try {
        branch = Branch.valueOf(branchType);
    } catch (IllegalArgumentException e) {
        System.out.println(branchType.toLowerCase() + " - invalid branch.");
        return;
    }
    // Check date of birth
    try {
        date = new Date(dob);
        if (!date.isValid()) {
            System.out.println("DOB invalid: " + dob + " is not a valid calendar date!");
            return;
        }
        if (date.isTodayOrFuture()) {
            System.out.println("DOB invalid: " + dob + " cannot be today or a future day.");
            return;
        }
        if (date.isUnder18()) {
            System.out.println("Not eligible to open: " + dob + " under 18!");
            return;
        }
    } catch (IllegalArgumentException e) {
        System.out.println("Invalid date format!");
        return;
    }
     // Check deposit 
     try {
        depositAmount = Double.parseDouble(amount);
    } catch (NumberFormatException e) {
        System.out.println("For input string: " + '"'+ amount.toLowerCase() +'"'+ " - not a valid amount.");
        return;
    }
    if (type == AccountType.MONEYMARKET && depositAmount < 2000) {
        System.out.println("Minimum of $2,000 to open a Money Market account.");
        return;   
    }
    if (depositAmount <= 0) {
        System.out.println("Initial deposit cannot be 0 or negative.");
        return;
    }
    // Check person
    Profile newPerson = new Profile(firstName, lastName, date);
    // Check if the person already has an account of the same type
    if (database.hasAccountOfType(newPerson, type)) {
      System.out.println(firstName +" "+lastName +" already has a " + type.name().toLowerCase() + " account.");
      return;
  }
  
   
    // Create new account   
    AccountNumber accountNumber = new AccountNumber(branch, type);
    Account newAccount = new Account(accountNumber, newPerson, depositAmount);
    database.add(newAccount);
    System.out.println(type.name()+ " account " + newAccount.getNumber() + " has been opened.");
}

/**
 * Closes an account after processing input.
 *
 * @param tokens An array of strings containing the account number or profile details.
 */
private void closeAccount(String[] tokens) {
    if (tokens.length < 2) {
        System.out.println("Missing data for closing account!");
        return;
    }
    // CASE 1:
    if (tokens.length == 2) {
        String accountNumberString = tokens[1].trim();
        AccountNumber accountNum = new AccountNumber(accountNumberString);
        //OVERLOAD THE METHODS TO MAKE IT WORK
        Account accountToClose = database.getAccount(accountNum);

        if (accountToClose == null) {
            System.out.println(accountNum + " account does not exist.");
            return;
        }
        //OVERLOAD THE METHODS TO MAKE IT WORK
        System.out.println(accountToClose);
        database.removeAccount(accountToClose);  // which should also archive i

        System.out.println(accountNumberString + "is closed and moved to archive; balance set to 0.");
    }

    // CASE 2: "C fName lName dob"
    if (tokens.length == 4) {
        String firstName = tokens[1].trim();
        String lastName = tokens[2].trim();
        String dobString = tokens[3].trim();

        Date dob = new Date(dobString);
        Profile profile = new Profile(firstName, lastName, dob);
        Account[] accountsToClose = database.findAccountsByProfile(profile);
        if (accountsToClose.length == 0) {
            System.out.println(firstName + " " + lastName + " does not have any accounts in the database.");
        } else {
            for (Account account : accountsToClose) {
                database.removeAccount(account);
            }
            System.out.println("All accounts for " + firstName + " " + lastName + " "+ dobString+" are closed and moved to archive, balance set to 0.");
        }
    }
}

/**
 * Withdraws from an account after processing input.
 *
 * @param tokens An array of strings containing the account number and withdrawal amount.
 */
private void withdrawProcess(String[] tokens){
    String accountNumberString = tokens[1].trim();
    String amountString = tokens[2].trim();
    double amount;
    try {
        amount = Double.parseDouble(amountString);
        if (amount <= 0) {
            System.out.println(amount + " withdrawal amount cannot be 0 or negative.");
            return;
        }
    } catch (NumberFormatException e) {
        System.out.println("For input string: " + amountString + " - not a valid amount.");
        return;
    }

    AccountNumber accountNum = new AccountNumber(accountNumberString);
    Account account = database.getAccount(accountNum);
    if (account == null) {
        System.out.println(accountNumberString + " does not exist.");
        return;
    }

    AccountType initialType = account.getNumber().getType();
    boolean success = database.withdraw(accountNum, amount);
    if (success) {
        AccountType finalType = account.getNumber().getType();
        if (initialType == AccountType.MONEYMARKET && finalType == AccountType.SAVINGS) {
            System.out.printf("%s is downgraded to SAVINGS - $%.2f withdrawn from %s%n", accountNumberString, amount, accountNumberString);
        } else {
            System.out.printf("$%.2f withdrawn from %s%n", amount, accountNumberString);
        }
    } else {
        System.out.printf("Withdrawal of $%.2f from account [%s] failed.%n", amount, accountNumberString);
    }
}

    /**
     * Deposits into an account after processing input.
     *
     * @param tokens An array of strings containing the account number and deposit amount.
     */
private void depositProcess(String[] tokens){
    String accountNumberString = tokens[1].trim();
    String amountString = tokens[2].trim();
    double amount;
    try {
        amount = Double.parseDouble(amountString);
        if (amount <= 0) {
            System.out.println(amount + " deposit amount cannot be 0 or negative.");
            return;
        }
    } catch (NumberFormatException e) {
        System.out.println("For input string: " + amountString + " - not a valid amount.");
        return;
    }

    AccountNumber accountNum = new AccountNumber(accountNumberString);
    Account account = database.getAccount(accountNum);
    if (account == null) {
        System.out.println(accountNumberString + " does not exist.");
        return;
    }

    database.deposit(accountNum, amount);
    System.out.printf("$%.2f deposited to %s%n", amount, accountNumberString);
}


}

