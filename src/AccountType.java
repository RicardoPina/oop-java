/**
 * enum representing different types of bank accounts
 * each account type has a unique 2-digit code */

public enum AccountType
{
    checking("01"),
    savings("02"),
    moneymarket("03");

    private final String code;

    /**
     * constructor for an AccountType enum */

    AccountType(String code)
    {
        this.code = code;
    }

    /**
     * gets the account type code */

    public String getCode()
    {
        return code;
    }

    /**
     * Returns the AccountType enum based on a string input (case insensitive) */

    public static AccountType fromString(String type)
    {
        for (AccountType accountType : AccountType.values())
        {
            if (accountType.name().equalsIgnoreCase(type.replace(" ", "_")))
            {
                return accountType;
            }
        }
        return null;
    }

    /**
     * returns a string representation of the account type */

    @Override
    public String toString()
    {
        return String.format("AccountType[%s] Code[%s]", this.name(), code);
    }
}