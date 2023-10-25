package edu.lehigh.cse216.rubber_ducks.backend;

import java.sql.Timestamp;

import org.postgresql.translation.messages_bg;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App
 */
public class DataRowTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataRowTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DataRowTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String propsal = "THis is the first testing propsal";
        int numLikes = 5;
        int id = 17;
        DataRow d = new DataRow(id, propsal, numLikes, new Timestamp(System.currentTimeMillis()));

        assertTrue(d.message.equals(propsal));
        assertTrue(d.numLikes == numLikes);
        assertTrue(d.mId == id);
        assertFalse(d.mCreated == null);
    }

    /**
     * Ensure that the copy constructor works correctly
     */
    public void testCopyconstructor() {
        String proposal = "Test company proposal";
        int numLikes = 0;
        int id = 177;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        DataRow d = new DataRow(id, proposal, numLikes, currentTime);
        DataRow d2 = new DataRow(d);
        assertTrue(d2.message.equals(d.message));
        assertTrue(d2.numLikes == d.numLikes);
        assertTrue(d2.mId == d.mId);
        assertTrue(d2.mCreated.equals(d.mCreated));
    }
}
