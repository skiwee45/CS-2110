package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CMSuTest {
    @Test
    void testHasConflict() {
        Course test1 = new Course("title", 4, "ray", "cornell", 12, 45, 60);
        Course test2 = new Course("title", 4, "ray", "cornell", 13, 45, 60);
        Course test3 = new Course("title", 4, "ray", "cornell", 13, 15, 60);

        Student goodStudent = new Student("A", "B");
        Student conflictStudent = new Student("C", "D");

        CMSu sys = new CMSu();

        test1.enrollStudent(goodStudent);
        test2.enrollStudent(goodStudent);
        test1.enrollStudent(conflictStudent);
        test2.enrollStudent(conflictStudent);
        test3.enrollStudent(conflictStudent);

        sys.addCourse(test1);
        sys.addCourse(test2);
        sys.addCourse(test3);

        sys.addStudent(goodStudent);
        sys.addStudent(conflictStudent);

        assertFalse(sys.hasConflict(goodStudent));
        assertTrue(sys.hasConflict(conflictStudent));
    }

    @Test
    void testAuditCredits() {
        Course test1 = new Course("title", 4, "ray", "cornell", 12, 45, 60);
        Course test2 = new Course("title", 4, "ray", "cornell", 13, 45, 60);
        Course test3 = new Course("title", 4, "ray", "cornell", 13, 15, 60);

        Student goodStudent = new Student("A", "B");
        Student conflictStudent = new Student("C", "D");

        CMSu sys = new CMSu();

        test1.enrollStudent(goodStudent);
        test2.enrollStudent(goodStudent);
        test1.enrollStudent(conflictStudent);
        test2.enrollStudent(conflictStudent);
        test3.enrollStudent(conflictStudent);

        sys.addCourse(test1);
        sys.addCourse(test2);
        sys.addCourse(test3);

        sys.addStudent(goodStudent);
        sys.addStudent(conflictStudent);

        assertEquals(1, sys.auditCredits(10).size());
        assertEquals(2, sys.auditCredits(5).size());
        assertEquals(0, sys.auditCredits(16).size());
    }

    @Test
    void testCreditConsistency() {
        Course test1 = new Course("title", 4, "ray", "cornell", 12, 45, 60);
        Course test2 = new Course("title", 4, "ray", "cornell", 13, 45, 60);
        Course test3 = new Course("title", 4, "ray", "cornell", 13, 15, 60);

        Student goodStudent = new Student("A", "B");
        Student conflictStudent = new Student("C", "D");
        conflictStudent.adjustCredits(1);

        CMSu sys1 = new CMSu();
        CMSu sys2 = new CMSu();

        test1.enrollStudent(goodStudent);
        test2.enrollStudent(goodStudent);
        test1.enrollStudent(conflictStudent);
        test2.enrollStudent(conflictStudent);
        test3.enrollStudent(conflictStudent);

        sys1.addCourse(test1);
        sys1.addCourse(test2);
        sys1.addCourse(test3);

        sys2.addCourse(test1);
        sys2.addCourse(test2);
        sys2.addCourse(test3);

        sys1.addStudent(goodStudent);
        sys2.addStudent(conflictStudent);

        assertTrue(sys1.checkCreditConsistency());
        assertFalse(sys2.checkCreditConsistency());
    }
    // Each method must be tested in at least two different scenarios.
}