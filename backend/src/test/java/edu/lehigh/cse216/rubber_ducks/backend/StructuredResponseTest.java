package edu.lehigh.cse216.rubber_ducks.backend;

import java.sql.Timestamp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StructuredResponseTest extends TestCase{

    /**
     * Create the test case
     * 
     * @param testName the name of the test case
     */
    public StructuredResponseTest(String testName){
        super(testName);
    }

    /**
     * 
     * @return suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(StructuredResponseTest.class);
    }

    /**
     * Ensure that the constructor properly populates the fields of the class
     */
    public void testConstructor() {
        String mStatus = "ok";
        String mMessage = "none";
        //generate a DataRow object for the purpose of testing
        DataRow tData = new DataRow(1, "This is a test DataRow proposal", 0, new Timestamp(System.currentTimeMillis()));
        //create the Structured response
        StructuredResponse test = new StructuredResponse(mStatus, mMessage, tData);

        assertTrue(test.mStatus.equals(mStatus));
        assertTrue(test.mMessage.equals(mMessage));
        assertTrue(test.mData.equals(tData)); //might trigger false?
    }
    
}
