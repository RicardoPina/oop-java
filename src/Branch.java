/**
 * Enum representing the branch locations of RU Bank.
 * each branch has a unique 3-digit branch code, city name, county, and zip code */

public enum Branch
{
    edison("100", "08817", "Middlesex"),
    bridgewater("200", "08807", "Somerset"),
    princeton("300", "08542", "Mercer"),
    piscataway("400", "08854", "Middlesex"),
    warren("500", "07057", "Somerset");

    private final String branchCode;
    private final String zip;
    private final String county;

    /**
     * constructor for a Branch enum */
    Branch(String branchCode, String zip, String county)
    {
        this.branchCode = branchCode;
        this.zip = zip;
        this.county = county;
    }

    /**
     * gets the branch code */
    public String getBranchCode()
    {
        return branchCode;
    }

    /**
     * gets the zip code of the branch */
    public String getZip()
    {
        return zip;
    }

    /**
     * gets the county where the branch is located*/

    public String getCounty()
    {
        return county;
    }

    /**
     * returns the branch enum based on the city name  */

    public static Branch fromCity(String city)
    {
        for (Branch branch : Branch.values())
        {
            if (branch.name().equalsIgnoreCase(city)) {
                return branch;
            }
        }
        return null;
    }

    public static Branch fromBranchCode(String branchCode) {
        for (Branch branch : Branch.values()) {
            if (branch.getBranchCode().equals(branchCode)) {
                return branch;
            }
        }
        return null;
    }

    /**
     * returns a string representation of the branch */
    @Override
    public String toString()
    {
        return String.format("Branch[%s] Code[%s] County[%s] Zip[%s]",
                this.name(), branchCode, county, zip);
    }
}