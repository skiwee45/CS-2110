package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentSetTest {
    @Test
    void testConstructorAndSize() {
        // Constructor should yield an empty set
        StudentSet students = new StudentSet();
        assertEquals(0, students.size());
    }

    @Test
    void testAddStudent() {
        {
            StudentSet students = new StudentSet();
            Student a = new Student("A", "AA");
            students.add(a);
            assertEquals(1, students.size());
            for (int i = 0; i < 10; i++) {
                Student b = new Student("B" + i, "BB");
                students.add(b);
            }
            assertEquals(11, students.size());
        }
    }

    @Test
    void testRemoveStudent() {
        {
            StudentSet students = new StudentSet();
            Student a = new Student("A", "AA");
            students.add(a);
            for (int i = 0; i < 10; i++) {
                Student b = new Student("B" + i, "BB");
                students.add(b);
            }
            assertTrue(students.remove(a));
            assertFalse(students.contains(a));
            assertEquals(10, students.size());
            assertFalse(students.remove(a));
        }
    }

    // Run this test suite and confirm that it fails.  Complete TODOs 9-10, then run it
    // again and confirm that it now passes.  As you proceed with TODOs 11-13, start by adding a
    // test case here for the method being implemented, then repeat the above (confirm that it
    // fails, implement the method, confirm that it passes).  This TODO is complete when there are
    // test cases covering all of `StudentSet`'s public methods.
    // Be sure that at least one test case triggers a resize, given your chosen initial capacity.


}
