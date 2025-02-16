
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
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return fname.equalsIgnoreCase(profile.fname) &&
               lname.equalsIgnoreCase(profile.lname) &&
               dob.equals(profile.dob);
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

    public static void main(String[] args) 
    {
        Date dob1 = new Date(2000, 5, 20);
        Date dob2 = new Date(1995, 10, 15);
        Date dob3 = new Date(2000, 5, 20); 

        Profile profile1 = new Profile("John", "Doe", dob1);
        Profile profile2 = new Profile("Alice", "Smith", dob2);
        Profile profile3 = new Profile("John", "Doe", dob3);

        System.out.println("Profile1 equals Profile2? " + profile1.equals(profile2)); //should be false
        System.out.println("Profile1 equals Profile3? " + profile1.equals(profile3)); //should be true

        // Testing toString()
        System.out.println("Profile1: " + profile1); //John Doe 5/20/2000
        System.out.println("Profile2: " + profile2); //Alice Smith 10/15/1995

        // Testing compareTo()
        System.out.println("Profile1 compared to Profile2: " + profile1.compareTo(profile2)); // > 0 (Doe > Smith)
        System.out.println("Profile1 compared to Profile3: " + profile1.compareTo(profile3)); // 0 (Same profile)
    }
}
