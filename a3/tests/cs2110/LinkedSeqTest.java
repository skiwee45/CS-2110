package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LinkedSeqTest {

    // Helper functions for creating lists used by multiple tests.  By constructing strings with
    // `new`, more likely to catch inadvertent use of `==` instead of `.equals()`.

    /**
     * Creates [].
     */
    static Seq<String> makeList0() {
        return new LinkedSeq<>();
    }

    /**
     * Creates ["A"].  Only uses prepend.
     */
    static Seq<String> makeList1() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B"].  Only uses prepend.
     */
    static Seq<String> makeList2() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B", "C"].  Only uses prepend.
     */
    static Seq<String> makeList3() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates a list containing the same elements (in the same order) as array `elements`.  Only
     * uses prepend.
     */
    static <T> Seq<T> makeList(T[] elements) {
        Seq<T> ans = new LinkedSeq<>();
        for (int i = elements.length; i > 0; i--) {
            ans.prepend(elements[i - 1]);
        }
        return ans;
    }

    @Test
    void testConstructorSize() {
        Seq<String> list = new LinkedSeq<>();
        assertEquals(0, list.size());
    }

    @Test
    void testPrependSize() {
        // List creation helper functions use prepend.
        Seq<String> list;

        list = makeList1();
        assertEquals(1, list.size());

        list = makeList2();
        assertEquals(2, list.size());

        list = makeList3();
        assertEquals(3, list.size());
    }

    @Test
    void testToString() {
        Seq<String> list;

        list = makeList0();
        assertEquals("[]", list.toString());

        list = makeList1();
        assertEquals("[A]", list.toString());

        list = makeList2();
        assertEquals("[A, B]", list.toString());

        list = makeList3();
        assertEquals("[A, B, C]", list.toString());
    }

    @Test
    void testContains() {
        Seq<String> list = makeList3();
        assertTrue(list.contains("C"));
        assertFalse(list.contains("D"));
        list.prepend("C");
        assertTrue(list.contains("C"));
    }

    @Test
    void testGet() {
        Seq<String> list = makeList3();
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void testAppend() {
        Seq<String> list = makeList0();
        list.append("A");
        list.append("B");
        list.append("C");
        assertEquals(makeList3().toString(), list.toString());
    }

    @Test
    void testInsertBefore() {
        Seq<String> list = makeList3();
        list.insertBefore("D", "A");
        list.insertBefore("E", "B");
        list.insertBefore("F", "C");
        assertEquals("[D, A, E, B, F, C]", list.toString());
    }

    @Test
    void testRemove() {
        Seq<String> list = makeList3();
        assertFalse(list.remove("D"));
        list.append("B");
        assertTrue(list.remove("B"));
        assertTrue(list.remove("C"));
        assertTrue(list.remove("B"));
        assertTrue(list.remove("A"));
        assertEquals("[]", list.toString());
    }

    @Test
    void testEquals() {
        Seq<String> list1 = makeList3();
        Seq<String> list2 = makeList2();
        Seq<String> list3 = makeList0();
        assertNotEquals(list1, list2);
        assertNotEquals(list1, list3);
        assertNotEquals(list2, list3);
        list1.remove("C");
        assertEquals(list1, list2);
        list1.remove("B");
        list1.remove("A");
        assertEquals(list1, list3);
    }

    // TODO: Add new test cases here as you implement each method in `LinkedSeq`.  You may combine
    // multiple tests for the _same_ method in the same @Test procedure, but be sure that each test
    // case is visibly distinct (comments are good for this).  You are welcome to compare against an
    // expected `toString()` output in order to check multiple aspects of the state at once (in
    // general, later tests may make use of methods that have previously been tested).



    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered `hashCode()` or `assertThrows()` in class.
     */

    @Test
    void testHashCode() {
        assertEquals(makeList0().hashCode(), makeList0().hashCode());

        assertEquals(makeList1().hashCode(), makeList1().hashCode());

        assertEquals(makeList2().hashCode(), makeList2().hashCode());

        assertEquals(makeList3().hashCode(), makeList3().hashCode());
    }

    @Test
    void testIterator() {
        Seq<String> list;
        Iterator<String> it;

        list = makeList0();
        it = list.iterator();
        assertFalse(it.hasNext());
        Iterator<String> itAlias = it;
        assertThrows(NoSuchElementException.class, () -> itAlias.next());

        list = makeList1();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());

        list = makeList2();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertFalse(it.hasNext());
    }
}
