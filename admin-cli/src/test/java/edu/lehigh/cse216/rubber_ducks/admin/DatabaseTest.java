package edu.lehigh.cse216.rubber_ducks.admin;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.ArrayList;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class DatabaseTest
    extends TestCase
{
    /**
     * Create the test case
     * 
     * @param testName name of the test case
     */
    public DatabaseTest( String testName )
    {
        super( testName );
    }


    /**
     *  @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DatabaseTest.class );
    }


    /**
     * Rigourous Test :-)
     */
    public void testDatabase()
    {
        assertTrue( true );
    }

    /**
     * Initializing the database
     */
    private Database dbt;

    /**
     * setting the database
     * 
     * creating a method to get the Postgres configuration 
     * from the environment and get a fully-configured 
     * connection to the database, or exit immediately
     * 
     * @return void
     */
    private void setDatabase(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        dbt = Database.getDatabase(ip, port, user, pass, "TestingTable");
        if (dbt == null){
            return;
        }
    }

    /**
     * disconnecting the database
     * 
     * creating a method to disconnect from the database
     * 
     * @return void
     */
    private void disconnectDatabase() {
        // Disconnecting from the database
        dbt.disconnect();
    }

    /**
     * testing creating a table
     * 
     * creating a method to test table creation
     * 
     * true if table was created
     * false if table was not created
     * 
     * @return void
     */
    public void testCreateTable() {
        setDatabase();
        assertNotNull(dbt);
        try {
            dbt.createTable();
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Unable to create table");
            assertTrue(false);
        } finally {
            disconnectDatabase();
        }
    }
    /**
     * testing inserting a row
     * 
     * creating a method to test row insertion
     * 
     * inserts a row with a default id and timestamp 
     * but with the idea: Test Message, and the number 
     * of likes = 0.
     * 
     * true if the number of rows inserted was 1
     * false if the number of rows inserted was not 1
     * 
     * @return void
     */
    public void testInsertRow() {
        setDatabase();
        int rowsInserted = dbt.insertRow("Test Message", 0);
        assertEquals("Unexpected number of inserted rows", 1, rowsInserted);
        disconnectDatabase();
    }
    /**
     * testing selecting a row
     * 
     * creating a method to test row selection
     * 
     * selects a row with the id 1 
     * 
     * true if the row returned isnt null
     * false if the row returned is null
     * 
     * @return void
     */
    public void testSelectRow() {
        setDatabase();
        RowData rowSelected = dbt.selectOne(1);
        assertNotNull(rowSelected);
        disconnectDatabase();
    }
    /**
     * testing selecting all rows
     * 
     * creating a method to test selecting all rows
     * 
     * selects all rows
     * 
     * true if the number of rows in the table is 
     * equal to the number of rows selected
     * false if the number of rows in the table is 
     * inequal to the number of rows selected
     * 
     * @return void
     */
    public void testSelectAll() {
        setDatabase();
        ArrayList<RowData> rowSelectedAll = dbt.selectAll();
        int size = dbt.getSize();
        System.out.println(rowSelectedAll);
        assertEquals("Unexpected number of selected rows", size, rowSelectedAll.size());
        disconnectDatabase();
    }


    /** 
     * creating a method to test updating a row
     * 
     * updates a row's numLikes
     * 
     * true if the number of rows updated is 
     * equal to 1
     * false if the number of rows updated is 
     * inequal to 1
     * 
     * @return void
     */
    public void testUpdateOne() {
        setDatabase();
        int newLikes = dbt.selectOne(1).mNumLikes;
        int rowsUpdated = dbt.updateOne(1, newLikes);
        assertEquals("Unexpected number of updated rows", 1, rowsUpdated);
        disconnectDatabase();
    }

    /** 
     * creating a method to test deleting a row
     * 
     * deletes a row
     * 
     * checks for if the row exsists,
     * if it doesnt exsist it iterates
     * to the next id (up to 1000) until
     * it finds a row which exsists
     * deletes the row that exists
     * 
     * true if the number of rows deleted is 
     * equal to 1
     * false if the number of rows deleted is 
     * inequal to 1
     * 
     * @return void
     */
    public void testDeleteRow() {
        int idNum = -1;
        setDatabase();
        for (int i = 1; i < 1000; i++) {
            if (dbt.selectOne(i)!= null) {
                idNum = i;
                break;
            }
        }
        int rowsDeleted = dbt.deleteRow(idNum);
        assertEquals("Unexpected number of deleted rows", 1, rowsDeleted);
        disconnectDatabase();
    }
    /**
     * testing dropping a table
     * 
     * creating a method to test table dropping
     * 
     * true if table was dropped
     * false if table was not dropped
     * 
     * @return void
     */
    public void testDropTable() {
        setDatabase();
        assertNotNull(dbt);
        try {
            dbt.dropTable();
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Table doesn't exist");
            disconnectDatabase();
            assertTrue(false);
        }finally{
            dbt.createTable();
            disconnectDatabase();
        }
    }

}
