package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseTest {
    @Test
    void testConstructorAndObservers() {
        Course test = new Course("title", 4, "ray", "cornell", 12, 45, 120);

        assertEquals("title", test.title());
        assertEquals(4, test.credits());
        assertEquals("Professor ray", test.instructor());
        assertEquals("cornell", test.location());
        assertEquals("12:45 PM", test.formatStartTime());
    }

    @Test
    void testOverlap() {
        Course test1 = new Course("title", 4, "ray", "cornell", 12, 45, 60);
        Course test2 = new Course("title", 4, "ray", "cornell", 13, 45, 60);
        Course test3 = new Course("title", 4, "ray", "cornell", 13, 15, 60);
        Course test4 = new Course("title", 4, "ray", "cornell", 9, 15, 360);

        assertFalse(test1.overlaps(test2));
        assertTrue(test1.overlaps(test3));
        assertTrue(test2.overlaps(test3));
        assertTrue(test4.overlaps(test3));
    }

    @Test
    void testAddAndDrop() {
        Course c = new Course("title", 4, "ray", "cornell", 12, 45, 120);
        Student s = new Student("Jerry", "Lin");

        c.enrollStudent(s);
        assertTrue(c.hasStudent(s));
        assertEquals(4, s.credits());
        assertFalse(c.enrollStudent(s));

        c.dropStudent(s);
        assertFalse(c.hasStudent(s));
        assertEquals(0, s.credits());
        assertFalse(c.dropStudent(s));
    }

    // already been implemented in the release code.  Confirm that the case initially fails, then
    // complete TODOs 15-16 and return here to confirm that your case now passes.
    // As you proceed with TODOs 17-22, start by adding a test case here for the method being
    // implemented, then repeat the above (confirm that it fails, implement the method, confirm that
    // it passes).  This TODO is complete when there are test cases covering all of `Course`'s
    // public methods.
    // Be sure to verify any effects on objects of other classes as well.


}
