
import java.util.Random;
/**
 * represents an account number composed of a branch code, account type, and a randomly generated 4-digit number.
 * implements Comparable to allow sorting based on account numbers 
 * 
 * @author Andrew Salama
 * */
public class AccountNumber implements Comparable<AccountNumber>
{
    // private static final int SEED = 9999;
    private Branch branch;
    private AccountType type;
    private String number;
    /**
     * Constructor to create an AccountNumber with a random 4-digit number*/

    public AccountNumber(Branch branch, AccountType type)
    {
        this.branch = branch;
        this.type = type;
        this.number = generateRandomNumber();
    }

    public AccountNumber(String numberToSearch) {
        if (numberToSearch.length() != 9) {
            return; // invalid account number
        }
        String branchCode = numberToSearch.substring(0, 3);
        this.branch = Branch.fromBranchCode(branchCode);
        if (this.branch == null) {
            return; // invalid branch code
        }
        String typeCode = numberToSearch.substring(3, 5);
        this.type = AccountType.fromCode(typeCode);
        this.number = numberToSearch.substring(5);
    }
    /**
     * generates a 4-digit random number using a fixed seed.
     * ensures the number remains within the range 1000-9999 
     * 
     * @returns the generated random number as a string
     * */

    private String generateRandomNumber()
    {
        Random random = new Random();
        int MIN_RANDOM = 1000;
        int MAX_RANDOM = 9999;
        int randNum = random.nextInt((MAX_RANDOM - MIN_RANDOM) + 1) + MIN_RANDOM;
        return String.valueOf(randNum);
    }
    /**
     * Returns the full 9-digit account number as a string
     * @returns the full 9-digit account number 
     * 
     * */

    @Override
    public String toString()
    {
        return String.format("%s%s%s", branch.getBranchCode(), type.getCode(), number);
    }

    /**
     * Compares two account numbers for sorting 
     * 
     * @returns the comparison of the two account numbers as a string
     * */

    @Override
    public int compareTo(AccountNumber other)
    {
        return this.toString().compareTo(other.toString());
    }

    /**
     * checks if two account numbers are equal based on their full 9-digit string
     * 
     * @overrides equals in class java.lang.Object
     * @returns true if the account numbers are equal, false otherwise
     *  */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountNumber that = (AccountNumber) obj;
        return this.toString().equals(that.toString());
    }

    /**
     * Sets the account type to savings if money market
     * balance is below 2,000 
     * 
     * @returns the account type
     * */
    public AccountType setType(AccountType type) {
        AccountType oldType = this.type;
        this.type = type;
        return oldType;
    }

    /**
     * gets the branch of the account number 
     * 
     * @returns the branch of the account number
     * */
    public Branch getBranch()
    {
        return branch;
    }
    /**
     * gets the account type of the account number 
     * 
     * @returns the account type of the account number
     * */
    public AccountType getType()
    {
        return type;
    }
    /**
     * gets the branch code of the account number 
     * 
     * @returns the branch code of the account number
     * */
    public String getBranchCode()
    {
        return branch.getBranchCode();
    }
    /**
     * gets the account type code of the account number 
     * 
     * @returns the account type code of the account number
     * */
    public String getNumber()
    {
        return number;
    }
}
