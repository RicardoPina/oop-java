/**
 * represents a bank account with a unique account number, account holder profile, and balance.
 * implements Comparable to allow sorting based on account number */

public class Account implements Comparable<Account>
{
    private AccountNumber number;
    private Profile holder;
    private double balance;

    /**
     * constructor to initialize an account */

    public Account(AccountNumber number, Profile holder, double balance)
    {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * deposits a specified amount into the account */

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    /**
     * withdraws a specified amount from the account if sufficient balance is available
     *
     * @return
     */

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance or invalid amount.");
        }
    }
    public void downgradeAccountType() {
        if (number.getType() == AccountType.MONEYMARKET && balance < 2000) {
            number.setType(AccountType.SAVINGS);
        }
    }
    /**
     * checks if two accounts are equal based on their account numbers */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return number.equals(account.number);
    }

    /**
     * provides a string representation of the account details */

    @Override
    public String toString()
    {
        return String.format("Account#[%s] Holder[%s] Balance[$%.2f] Branch [%s]",
                number.toString(), holder.toString(), balance, number.getBranch().name());
    }

    /**
     * compares two accounts based on their account numbers*/

    @Override
    public int compareTo(Account other)
    {
        return this.number.compareTo(other.number);
    }

    //getters
    public AccountNumber getNumber()
    {
        return number;
    }

    public Profile getHolder()
    {
        return holder;
    }

    public double getBalance()
    {
        return balance;
    }
}