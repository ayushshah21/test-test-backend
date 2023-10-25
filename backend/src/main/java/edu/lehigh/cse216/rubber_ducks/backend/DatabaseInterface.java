package edu.lehigh.cse216.rubber_ducks.backend;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface for a Database class
 * Methods are common database operations
 */
public interface DatabaseInterface {
    /**
     * Method to insert a row into the database
     * @param proposal the text of a new idea
     * @param numLikes the number of likes for the proposal
     * @return the number of rows added
     */
    int insertRow(String proposal, int numLikes);
    /**
     * Returns a list of all rows in the database instance
     * @return the number of rows returned
     */
    List<DataRow> selectAll();
    /**
     * Returns a single row of the database for the given id
     * @param id the id of the row being selected
     * @return the DataRow representation of the selected row
     */
    DataRow selectOne(int id);
    /**
     * Deletes the row of the given id in the database
     * @param id id of the row being deleted
     * @return the number of rows deleted
     */
    int deleteRow(int id);
    /**
     * Updates one row in the database. It modifies the number of likes
     * @param id id of the row being updated
     * @param numLikes number of likes being added 
     * @return the number of rows updated (1 if successful)
     */
    int updateOne(int id, int numLikes);
    /**
     * Creates a table by calling a prepared statement
     */
    void createTable();
    /**
     * Drops a table using prepared statements
     */
    void dropTable();
}