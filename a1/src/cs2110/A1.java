package cs2110;

/*
 * Assignment metadata
 * Name and NetID: Raymond Lin (rpl67)
 * Hours spent on assignment: 2
 */

import java.util.Arrays;

/**
 * Collection of misc. static functions for showcasing the capabilities of Java in a procedural
 * context.
 */
public class A1 {

    /**
     * Return the area of a regular polygon with `nSides` sides of length `sideLength`. Units of
     * result are the square of the units of `sideLength`. Requires `nSides` is at least 3,
     * `sideLength` is non-negative.
     */
    public static double polygonArea(int nSides, double sideLength) {
        // TODO: Implement this method according to its specifications.
        double centerAngle = 2 * Math.PI / nSides;
        double radius = (sideLength / 2) / Math.tan(centerAngle / 2);
        return radius * sideLength / 2 * nSides;
    }

    /**
     * Return the next term in the Collatz sequence after the argument.  If the argument is even,
     * the next term is it divided by 2.  If the argument is odd, the next term is 3 times it plus
     * 1.  Requires magnitude of odd `x` is less than `Integer.MAX_VALUE/3` (otherwise overflow is
     * possible).
     */
    // TODO: Declare and implement a (public, static) method named `nextCollatz()` that takes one
    // int argument and returns an int.
    public static int nextCollatz(int term) {
        if (term % 2 == 0) {
            return term / 2;
        } else {
            return 3 * term + 1;
        }
    }

    /**
     * Return the sum of the Collatz sequence starting at `seed` and ending at 1 (inclusive).
     * Requires `seed` is positive, sum does not overflow.
     */
    public static int collatzSum(int seed) {
        int term = seed;
        int sum = term;
        while (term != 1) {
            term = nextCollatz(term);
            sum += term;
        }
        // TODO: Implement this method according to its specifications.
        return sum;
    }

    /**
     * Return the median value among `{a, b, c}`.  The median has the property that at least half of
     * the elements are less than or equal to it and at least half of the elements are greater than
     * or equal to it.
     */
    public static int med3(int a, int b, int c) {
        int[] arr = new int[] {a, b, c};
        Arrays.sort(arr);
        return arr[1];
    }

    /**
     * Return whether the closed intervals `[lo1, hi1]` and `[lo2, hi2]` overlap.  Two intervals
     * overlap if there exists a number contained in both of them.  Notation: the interval `[lo,
     * hi]` contains all numbers greater than or equal to `lo` and less than or equal to `hi`.
     * Requires `lo1` is less than or equal to `hi1` and `lo2` is less than or equal to `hi2`.
     */
    public static boolean intervalsOverlap(int lo1, int hi1, int lo2, int hi2) {
        return (hi1 >= lo2 && lo1 <= lo2) || (hi2 >= lo1 && lo2 <= hi1);
        // TODO: Implement this method according to its specifications.
    }

    /**
     * Return the approximation of pi computed from the sum of the first `nTerms` terms of the
     * Madhava-Leibniz series.  This formula states that pi/4 = 1 - 1/3 + 1/5 - 1/7 + 1/9 - ...
     * Requires `nTerms` is non-negative.
     */
    public static double estimatePi(int nTerms) {
        double piOverFour = 0;
        double alternating = 1;
        for (int i = 0; i < nTerms; i++){
            piOverFour += alternating / (1 + 2 * i);
            alternating *= -1;
        }
        // TODO: Implement this method according to its specifications.
        return piOverFour * 4;
    }

    /**
     * Returns whether the sequence of characters in `s` is equal (case-sensitive) to that sequence
     * in reverse order.
     */
    public static boolean isPalindrome(String s) {
        // Implementation constraint: Use the `charAt()` and `length()` methods
        // of the `String` class.
        for (int i = 0; i < s.length() / 2; i++) {
            if(s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return an order confirmation message in English containing the order ID and the number of
     * items it contains.  Message shall handle item plurality properly (e.g. "1 item" vs. "3
     * items") and shall surround the order ID in single quotes. Examples:
     * <pre>
     * formatConfirmation("123ABC", 1) should return
     *   "Order '123ABC' contains 1 item."
     * formatConfirmation("XYZ-999", 3)" should return
     *   "Order 'XYZ-999' contains 3 items."
     * </pre>
     * Requires `orderId` only contains digits, hyphens, or letters 'A' - 'Z'; `itemCount` is
     * non-negative.
     */
    public static String formatConfirmation(String orderId, int itemCount) {
        String plurality;
        if (itemCount == 1) {
            plurality = " item.";
        } else {
            plurality = " items.";
        }
        return "Order '" + orderId + "' contains " + itemCount + plurality;
        // TODO: Implement this method according to its specifications.
    }

    // TODO: Declare, document, and implement a `main()` method calling the above methods and
    // printing a result.
    public static void main(String[] args) {
        int i1 = 4;
        int i2 = 10;
        int i3 = 7;
        int med = med3(i1, i2, i3);
        int collatz = collatzSum(med);
        double piApprox = estimatePi(10);
        double area = polygonArea(collatz, piApprox);
        System.out.printf("The area of a polygon with %d sides of length %f m is %f m^2.", collatz, piApprox, area);
    }
}
