package cs2110;

/**
 * A student tracked by the CMSμ course management system.
 */
public class Student {

    //first name of the student, cannot be null or empty string
    private final String f_name;
    //last name of the student, cannot be null or empty string
    private final String l_name;
    //total number of credits that the student is taking, cannot be negative
    private int credits;


    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert f_name != null && !f_name.isEmpty();
        assert l_name != null && !l_name.isEmpty();
        assert credits >= 0;
    }

    /**
     * Create a new Student with first name `firstName` and last name `lastName` who is not enrolled
     * for any credits.  Requires firstName and lastName are not empty.
     */
    public Student(String firstName, String lastName) {
        // Assert that all preconditions are met.
        // Assert that the class invariant is satisfied before returning.
        // (These two assertions are not, in general, redundant - one pertains to arguments provided
        // by the client, while the other pertains to the work done by the constructor.)
        // Note: If a field's initial value is independent of constructor arguments, it is legal to
        // initialize it when it is declared (or even to rely on a default value), but prefer to
        // assign it in the constructor so that the whole state is initialized in one place.
        assert firstName != null && !firstName.isEmpty();
        assert lastName != null && !lastName.isEmpty();
        f_name = firstName;
        l_name = lastName;
        credits = 0;
        assertInv();
    }

    /**
     * Return the first name of this Student.  Will not be empty.
     */
    public String firstName() {
        return f_name;
    }

    /**
     * Return the last name of this Student.  Will not be empty.
     */
    public String lastName() {
        return l_name;
    }

    /**
     * Return the full name of this student, formed by joining their first and last names separated
     * by a space.
     */
    public String fullName() {
        // Observe that, by invoking methods instead of referencing this fields, this method was
        // implemented without knowing how you will name your fields.
        return firstName() + " " + lastName();
    }

    /**
     * Return the number of credits this student is currently enrolled in.  Will not be negative.
     */
    public int credits() {
        return credits;
    }

    /**
     * Change the number of credits this student is enrolled in by `deltaCredits`. For example, if
     * this student were enrolled in 12 credits, then `this.adjustCredits(3)` would result in their
     * credits changing to 15, whereas `this.adjustCredits(-4)` would result in their credits
     * changing to 8.  Requires that the change would not cause the student's credits to become
     * negative.
     */
    void adjustCredits(int deltaCredits) {
        assert credits + deltaCredits >= 0 : " negative credits";
        credits += deltaCredits;
        assertInv();
    }

    /**
     * Return the full name of this student as its string representation.
     */
    @Override
    public String toString() {
        return fullName();
    }
}
