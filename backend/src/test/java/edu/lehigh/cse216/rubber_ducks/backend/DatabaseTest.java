package edu.lehigh.cse216.rubber_ducks.backend;

import java.util.ArrayList;

import org.junit.Before;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for the MockDatabase class
 */
public class DatabaseTest extends TestCase{

    MockDatabase mdb = new MockDatabase();
    
    @Before
    public void setUp() throws Exception{

        mdb.insertRow("Testing my proposals", 0);
        mdb.insertRow("This is a great proposal", 10);

    }

    /**
     * Create the test case
     * 
     * @param testName name of testcase
     */
    public DatabaseTest(String testName){
        super(testName);
    }

    /**
     * 
     * @return suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }

    
    @org.junit.Test
    public void testInsertRow(){
        //check that the index increases after the insert
        int curIn = mdb.index; //ignore the warning hehe

        mdb.insertRow("This is the first row", 0);

        //now check that the index increased

        int newIn = mdb.index;

        assertEquals(newIn,(curIn+1));
    }

    @org.junit.Test
    public void testSelectAll(){
        //check that the returned list has the same length as the simedDB
        int dbLen = mdb.simedDatabase.size();

        ArrayList<DataRow> result = (ArrayList<DataRow>) mdb.selectAll();

        int resultLen = result.size();

        assertEquals(dbLen,resultLen);
    }

    @org.junit.Test
    public void testSelectOne(){
        //I know the contents of id=1
        String prop = "Testing my proposals";
        int liked = 0;

        DataRow res = mdb.selectOne(1);

        assertEquals(prop,res.message);
        assertEquals(liked,res.numLikes);
    }

    @org.junit.Test
    public void testDeleteRow(){
        //lets delete the row with id 1. Delete should return that id's index
        int expected = mdb.findIndex(2);
        int actual = mdb.deleteRow(2);

        assertEquals(expected,actual);
    }

    @org.junit.Test
    public void testUpdateOne(){
        //for id of 2, want to change the number of likes
        int startingLikes = mdb.simedDatabase.get(2).numLikes;

        int rowsUpdated = mdb.updateOne(2, 2);

        assertTrue(rowsUpdated == 1);
        assertEquals(mdb.simedDatabase.get(mdb.findIndex(2)).numLikes, 2);
    }
}
