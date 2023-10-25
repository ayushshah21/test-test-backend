package edu.lehigh.cse216.rubber_ducks.admin;
import java.sql.Timestamp;

/**
     * RowData is like a struct in C: we use it to hold data, and we allow 
     * direct access to its fields.  In the context of this Database, RowData 
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database.  RowData and the 
     * Database are tightly coupled: if one changes, the other should too.
     */
    public class RowData {
        /**
         * The ID of this row of the database
         */
        int mId;
        /**
         * The subject stored in this row
         */
        int mNumLikes;
        /**
         * The message stored in this row
         */
        String mIdea;
        /**
         * The time at which this row was created
         */
        Timestamp mDatecreated;

        /**
         * Construct a RowData object by providing values for its fields
         * @param id   The id of the row
         * @param idea the idea contained in the row (this will be the body of the message)
         * @param numLikes the number of likes the row has (0 or 1)
         * @param datecreated The date and time when the row was made
         */
        public RowData(int id, String idea, int numLikes, Timestamp datecreated) {
            mId = id;
            mNumLikes = numLikes;
            mIdea = idea;
            mDatecreated = datecreated;
        }
    }