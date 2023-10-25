package edu.lehigh.cse216.rubber_ducks.backend;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock database class that has the same functionality as a Live database
 * The purpose of this class is to test database operations
 */
public class MockDatabase implements DatabaseInterface {
    //a list of DataRow objects to simulate a database table
    //these are static variables since they should remain the same among all instances of MockDatabase
    static ArrayList<DataRow> simedDatabase = new ArrayList<>();
    static int index = 1;

    @Override
    public int insertRow(String proposal, int numLikes) {
        //create a DataRow and add it to the list
        simedDatabase.add(new DataRow(index, proposal, numLikes, new Timestamp(System.currentTimeMillis())));
        index ++;
        return 1; // Return the expected result
    }

    @Override
    public List<DataRow> selectAll() {
        // Simulate the select all operation
        ArrayList<DataRow> dataRows = new ArrayList<>();
        //create new copy of the simedDatabase
        dataRows =  (ArrayList<DataRow>)simedDatabase.clone();
        
        // Add test data to the list
        return dataRows;
    }

    @Override
    public DataRow selectOne(int id) {
        // Simulate the select one operation
        int dbIndex = findIndex(id);
        if (dbIndex < index) {
            return simedDatabase.get((id));
        } else {
            return null;
        }
    }

    @Override
    public int deleteRow(int id) {
        // First search the "database" and look for the value
        // int dbIndex = -1;
        // for (DataRow dR : simedDatabase){
        //     if (dR.mId == id){
        //         dbIndex = simedDatabase.indexOf(dR);
        //         break;
        //     }
        // }
        int dbIndex = findIndex(id);
        if (id == -1) {
            return 0; // Row not found
        } else {
            simedDatabase.remove(dbIndex);
            return dbIndex; //row was found
        }
    }

    @Override
    public int updateOne(int id, int numLikes) {
        // Simulate the update operation
        int dbIndex = findIndex(id);
        if (id == -1) {
            return 0; // Row does not exist
        } else {
            simedDatabase.get(dbIndex).numLikes = numLikes; //set the likes in the row
            return 1;
        }
    }

    @Override
    public void createTable() {
        // Simulate table creation
        simedDatabase = new ArrayList<>(); //generate the ArrayList
    }

    @Override
    public void dropTable() {
        // Simulate table dropping
        simedDatabase = null; //set the var to null
    }

    /**
     * Method to find the index of an id
     * This is necessary since we are using an arraylist and id doesnt necessarily equal index
     * @param id id being searched for
     * @return the index in the arraylist for the id
     */
    public int findIndex(int id){
        int dbIndex = -1;
        for (DataRow dR : simedDatabase){
            if (dR.mId == id){
                dbIndex = simedDatabase.indexOf(dR);
                break;
            }
        }
        return dbIndex;
    }
}
