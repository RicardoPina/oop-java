public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public boolean isValid() {} //check if the date is a valid calendar date
}

/*
You can add necessary constants, constructors, and methods; however, you CANNOT change or add
instance variables, -2 points for each violation.
You MUST override equals(), toString() and the comapreTo() methods, with the @Override tags. -2
points for each violation.
*/

/*
The isValid() method checks if a date is a valid calendar date.

For the months of January, March, May, July, August, October, and December, each has 31 days; April,
June, September, and November each has 30 days; February has 28 days in a non-leap year, and 29
days in a leap year. DO NOT use magic numbers for the months, days, and years. You can use the
constants defined in the Calendar class or define your own constant names. Below are examples
for defining the constant names.

public static final int QUADRENNIAL = 4;
public static final int CENTENNIAL = 100;
public static final int QUATERCENTENNIAL = 400;

To determine whether a year is a leap year, follow these steps:
Step 1. If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
Step 2. If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
Step 3. If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
Step 4. The year is a leap year.
Step 5. The year is not a leap year.

You MUST include a testbed main() in this class or lose 12 points. You CAN use System.out in the
testbed main() to display the test results.
*/