/**
 * represents a date with year, month, and day.
 * implements Comparable for sorting dates.
 * includes a method to check if a date is valid 
 * 
 * @author Andrew Salama
 * */
import java.util.Calendar;
import java.util.StringTokenizer;
public class Date implements Comparable<Date>
{
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    // Days in each month
    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * constructor for the Date class 
     * 
     * */

    public Date(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public Date(String dob) {
        int[] dateParts = parseDate(dob); // Call the parsing helper method
        if (dateParts == null) {
            System.out.println("DOB invalid: " + dob + "is not a valid calendar date!");
        }

        this.month = dateParts[0];
        this.day = dateParts[1];
        this.year = dateParts[2];
       }

           public boolean isTodayOrFuture() {
        Calendar today = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day); // Calendar month is 0-based

        return !date.before(today);
    }

    public boolean isUnder18() {
        Calendar today = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day); // Calendar month is 0-based

        today.add(Calendar.YEAR, -18);
        return date.after(today);
    }


    private static int[] parseDate(String dateStr) { // Helper function for parsing
        if (dateStr == null) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(dateStr, "/"); // Use StringTokenizer

        if (tokenizer.countTokens() != 3) {
            return null; // Incorrect number of parts
        }

        try {
            int month = Integer.parseInt(tokenizer.nextToken());
            int day = Integer.parseInt(tokenizer.nextToken());
            int year = Integer.parseInt(tokenizer.nextToken());
            return new int[]{month, day, year};

        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * checks if the date is a valid calendar date 
     * */

    public boolean isValid()
    {
        if (month < 1 || month > 12) return false;

        int maxDays = DAYS_IN_MONTH[month];
        if (month == 2 && isLeapYear()) {
            maxDays = 29;
        }

        return day > 0 && day <= maxDays;
    }

    /**
     * determines if the year is a leap year 
     * */

    private boolean isLeapYear()
    {
        if (year % QUADRENNIAL == 0)
        {
            if (year % CENTENNIAL == 0)
            {
                return year % QUATERCENTENNIAL == 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Compares this date with another date 
     * */
    @Override
    public int compareTo(Date other)
    {
        if (this.year != other.year)
        {
            return this.year - other.year;
        }
        if (this.month != other.month)
        {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    /**
     * checks if two dates are equal 
     * */

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date date = (Date) obj;
        return year == date.year && month == date.month && day == date.day;
    }

    /**
     * provides a string representation of the date 
     * */

    @Override
    public String toString()
    {
        return String.format("%d/%d/%d", month, day, year);
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    /**
     * Testbed main() method to test the Date class
     */
    public static void main(String[] args) {
        Date validDate1 = new Date(2024, 2, 29);
        Date validDate2 = new Date(2023, 12, 31);
        System.out.println("Is " + validDate1 + " valid? " + validDate1.isValid()); //should be true
        System.out.println("Is " + validDate2 + " valid? " + validDate2.isValid()); //should be true

        //invalid date tests
        Date invalidDate1 = new Date(2023, 2, 29);
        Date invalidDate2 = new Date(2023, 4, 31);
        Date invalidDate3 = new Date(2023, 13, 10);
        System.out.println("Is " + invalidDate1 + " valid? " + invalidDate1.isValid()); //should be false
        System.out.println("Is " + invalidDate2 + " valid? " + invalidDate2.isValid()); //should be false
        System.out.println("Is " + invalidDate3 + " valid? " + invalidDate3.isValid()); //should be false

        //testing equals() and compareTo()
        Date dateA = new Date(2023, 5, 20);
        Date dateB = new Date(2023, 5, 20);
        Date dateC = new Date(2022, 10, 10);
        System.out.println("Are " + dateA + " and " + dateB + " equal? " + dateA.equals(dateB)); //should be true
        System.out.println("Compare " + dateA + " and " + dateC + ": " + dateA.compareTo(dateC)); // > 0
    }
}