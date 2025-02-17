

/**
 * represents a bank account holder's profile, including first name, last name, and date of birth
 * implements Comparable for sorting profiles by last name, first name, and date of birth */

public class Profile implements Comparable<Profile>
{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * constructor to initialize a Profile */

    public Profile(String fname, String lname, Date dob)
    {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * checks if two profiles are equal based on first name, last name, and date of birth */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile other = (Profile) obj;

        // Manual null checks and comparisons
        if (fname == null) {
            if (other.fname != null) return false;
        } else if (!fname.equalsIgnoreCase(other.fname)) return false;

        if (lname == null) {
            if (other.lname != null) return false;
        } else if (!lname.equalsIgnoreCase(other.lname)) return false;

        if (dob == null) {
            if (other.dob != null) return false;
        } else if (!dob.equals(other.dob)) return false;

        return true; // All fields are equal
    }

    /**
     * provides a string representation of the profile details */

    @Override
    public String toString()
    {
        return String.format("%s %s %s", fname, lname, dob.toString());
    }

    /**
     * Compares two profiles based on last name, first name, and date of birth */

    @Override
    public int compareTo(Profile other)
    {
        int lastNameComparison = this.lname.compareToIgnoreCase(other.lname);
        if (lastNameComparison != 0) {
            return Integer.compare(lastNameComparison, 0); // Converts raw difference to -1, 0, 1
        }
        int firstNameComparison = this.fname.compareToIgnoreCase(other.fname);
        if (firstNameComparison != 0) {
            return Integer.compare(firstNameComparison, 0);
        }
        return this.dob.compareTo(other.dob);
    }

    public String getFname()
    {
        return fname;
    }

    public String getLname()
    {
        return lname;
    }

    public Date getDob()
    {
        return dob;
    }

    /**
     * Testbed main() method to test Profile functionality.
     * This method is required for grading */

    public static void main(String[] args) {
        Profile p1 = new Profile("John", "Doe", new Date(2, 19, 2000));
        Profile p2 = new Profile("John", "Doe", new Date(2, 19, 2000)); // Same data as p1
        Profile p3 = new Profile("john", "doe", new Date(2, 19, 2000)); // Same data as p1, different case
        Profile p4 = new Profile("Jane", "Doe", new Date(2, 19, 2000)); // Different first name
        Profile p5 = new Profile("John", "Doe", new Date(2, 20, 2000)); // Different DOB
        Profile p6 = new Profile("John", "Doe", null); // Null DOB
        Profile p7 = new Profile("John", null, new Date(2, 19, 2000)); // Null last name

        System.out.println("p1 equals p2: " + p1.equals(p2)); // Should be true
        System.out.println("p1 equals p3: " + p1.equals(p3)); // Should be true (case-insensitive)
        System.out.println("p1 equals p4: " + p1.equals(p4)); // Should be false
        System.out.println("p1 equals p5: " + p1.equals(p5)); // Should be false
        System.out.println("p1 equals p6: " + p1.equals(p6)); // Depends on your null handling logic
        System.out.println("p1 equals p7: " + p1.equals(p7)); // Depends on your null handling logic
    }
}
