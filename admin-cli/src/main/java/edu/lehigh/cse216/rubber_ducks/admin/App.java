package edu.lehigh.cse216.rubber_ducks.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;



/**
 * App is our basic admin app.  For now, all it does is connect to the database
 * and then disconnect
 */
public class App {
    
    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create tblData");
        System.out.println("  [D] Drop tblData");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [~] Update a row's likes");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "TD1*-+~q?";

        // We repeat until a valid single-character option is selected        
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String idea
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param idea A idea to display when asking for input
     * 
     * @return The string that the user provided.  May be "".
     */
    static String getString(BufferedReader in, String idea) {
        String s;
        try {
            System.out.print(idea + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param idea A idea to display when asking for input
     * 
     * @return The integer that the user provided.  On error, it will be -1
     */
    static int getInt(BufferedReader in, String idea) {
        int i = -1;
        try {
            System.out.print(idea + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options.  Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String databaseTable = env.get("POSTGRES_databaseTable");

        // Get a fully-configured connection to the database, or exit 
        // immediately
        Database db = Database.getDatabase(ip, port, user, pass, databaseTable);
        if (db == null)
            return;

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            //     function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {
                db.createTable();
            } else if (action == 'D') {
                String confirm = getString(in, "Are you sure you want to drop the table? \n\tType Y to confirm. \n\tType basically anything else to cancel.");
                if (confirm.equals("Y")){
                    System.out.printf("Dropping Table.\n");
                    db.dropTable();
                } else{
                    System.out.printf("Table drop cancelled.\n");
                }
            } else if (action == '1') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                RowData res = db.selectOne(id);
                if (res != null) {
                    System.out.printf("\tid\tLike Status\tDate Created\t\t\tPost Contents\n");
                    System.out.printf("\t[" + res.mId + "] " + "\t" + res.mNumLikes + "\t\t" + res.mDatecreated + "\t" + res.mIdea + "\n");
                }
            } else if (action == '*') {
                ArrayList<RowData> res = db.selectAll();
                if (res == null)
                    continue;
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                System.out.printf("\tid\tLike Status\tDate Created\t\t\tPost Contents\n");
                for (RowData rd : res) {
                    System.out.printf("\t[" + rd.mId + "] " + "\t" + rd.mNumLikes + "\t\t" + rd.mDatecreated + "\t" + rd.mIdea +"\n");
                }
            } else if (action == '-') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                int res = db.deleteRow(id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
            } else if (action == '+') {
                String idea = getString(in, "Enter the idea");
                if (idea.equals(""))
                    continue;
                int res = db.insertRow(idea, 0);
                System.out.println(res + " rows added");
            } else if (action == '~') {
                int id = getInt(in, "Enter the row ID :> ");
                if (id == -1){
                    continue;
                }
                int newLikes = db.selectOne(id).mNumLikes;
                int res = -1;
                if (newLikes == 0) {
                    res = db.updateOne(id, 1);
                } else {
                    res = db.updateOne(id, 0);
                }
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            }
        }
        // Always remember to disconnect from the database when the program 
        // exits
        db.disconnect();
    }
}