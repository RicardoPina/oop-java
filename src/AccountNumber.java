
import java.util.Random;
/**
 * represents an account number composed of a branch code, account type, and a randomly generated 4-digit number.
 * implements Comparable to allow sorting based on account numbers */

public class AccountNumber implements Comparable<AccountNumber>
{
    private static final int SEED = 9999;
    private static final int RANDOM_DIGITS = 4;
    private static final int MIN_RANDOM = 1000;
    private static final int MAX_RANDOM = 9999;

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

    public AccountNumber(String numberToSearch){
        this.number = numberToSearch;
    }
    /**
     * generates a 4-digit random number using a fixed seed.
     * ensures the number remains within the range 1000-9999 */

    private String generateRandomNumber()
    {
        Random random = new Random();
        int randNum = random.nextInt((MAX_RANDOM - MIN_RANDOM) + 1) + MIN_RANDOM;
        return String.valueOf(randNum);
    }
    /**
     * returns the full 9-digit account number */

    @Override
    public String toString()
    {
        return String.format("%s%s%s", branch.getBranchCode(), type.getCode(), number);
    }

    /**
     * Compares two account numbers for sorting */

    @Override
    public int compareTo(AccountNumber other)
    {
        return this.toString().compareTo(other.toString());
    }

    /**
     * checks if two account numbers are equal based on their full 9-digit string */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountNumber that = (AccountNumber) obj;
        return this.toString().equals(that.toString());
    }


    public Branch getBranch()
    {
        return branch;
    }

    public AccountType getType()
    {
        return type;
    }

    public String getBranchCode()
    {
        return branch.getBranchCode();
    }

    public String getNumber()
    {
        return number;
    }
}
