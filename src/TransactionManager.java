import java.util.Scanner;

public class TransactionManager {
    private final AccountDatabase database = AccountDatabase.getInstance();
    // Default constructor
    public TransactionManager(){
    }

    enum Commands {
        O, C, D, W, P, PA, PB, PH, PT, Q
    }

    public void run() {
        System.out.println("System is up and running.");
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
        System.out.println("Collection Manager terminated.");
    }

    private void processCommand(String[] tokens){
        String input = tokens[0].trim();
        Commands command = null;
        command = Commands.valueOf(input);
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

                    break;
                case PA:
                    database.printArchive();
//
                    break;
                case PB:
//                    AccountDatabase.printByBranch();
                    System.out.println("Selected: Print branch-phabetical accounts");
                    break;
                case PH:
//                    AccountDatabase.printByHolder();
                    System.out.println("Selected: Print ordered by the account holder’s profile");
                    break;
                case PT:
//                    AccountDatabase.printByType();
                    System.out.println("Selected: Print ordered by account type");
                    break;
                case Q:
                    System.out.println("Transaction Manager is terminated\n");
                    break;
                default:
                    System.out.println("Invalid command!");
            }
    }

private void openAccount(String[] tokens){
    //The token might be in lowercase, upper case or any combination.
    // add if statements to add messages for incorrect input
    String accountType = tokens[1].trim().toLowerCase();
    String branchType = tokens[2].trim().toLowerCase();
    String firstName = tokens[3].trim();
    String lastName = tokens[4].trim();
    String dob = tokens[5].trim();
    String amount = tokens[6].trim();
    double depositAmount = Double.parseDouble(amount);

    AccountType type = AccountType.valueOf(accountType);
    Branch branch = Branch.valueOf(branchType);
    /////////////////THis is the code im trying to fix///////////////////////////////////////
    Date date = new Date(dob);
    /////////////////////////////////////////////////////////
    Profile newPerson = new Profile(lastName, firstName, date);
    AccountNumber accountNumber = new AccountNumber(branch, type);
    Account newAccount = new Account(accountNumber, newPerson, depositAmount);
    AccountDatabase.getInstance().add(newAccount);
    System.out.println("Account opened: " + newAccount);
}

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
        Account accountToClose = AccountDatabase.getInstance().getAccount(accountNum);

        if (accountToClose == null) {
            System.out.println("Account not found.");
            return;
        }
        //OVERLOAD THE METHODS TO MAKE IT WORK
        System.out.println("Account to close" + accountToClose);
        AccountDatabase.getInstance().removeAccount(accountToClose);  // which should also archive i

        System.out.println("Account [" + accountNumberString + "] closed.");
    }

    // CASE 2: "C fName lName dob"
    if (tokens.length == 4) {
        String firstName = tokens[1].trim();
        String lastName = tokens[2].trim();
        String dobString = tokens[3].trim();

        Date dob = new Date(dobString);
        Profile profile = new Profile(firstName, lastName, dob);


    }
}

private void withdrawProcess(String[] tokens){}

private void depositProcess(String[] tokens){}
}

