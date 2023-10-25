package edu.lehigh.cse216.rubber_ducks.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;

import com.google.gson.Gson;

import spark.Spark;
import spark.servlet.SparkApplication;
import spark.utils.IOUtils;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase implements SparkApplication {

    private MockDatabase mdb = new MockDatabase();

    final Gson gson = new Gson();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    @Override
    public void init() {

        //define get route
        Spark.get("/proposals", (request, response) -> {
            response.type("application/json");
            return gson.toJson(new StructuredResponse("ok", null, mdb.selectAll()));
        });

        //define put route
        Spark.put("/proposals/:id/:liking", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            String isLiking = request.params("liking");
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            int result = 0;
            //checks if the provided route is for liking or unliking the given post/proposal
            if (isLiking.equals("true")){
                result = mdb.updateOne(idx,1);
            }
            else { //should be false
                result = mdb.updateOne(idx,0);
            }
            //check if the update worked properly
            if (result == 0) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

        //define post route
        Spark.post("/proposals", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = mdb.insertRow(req.proposalText, req.numLikes);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });

        //define delete route
        Spark.delete("/proposals/:id", (request, response) -> {
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the
            // message sent on a successful delete
            int result = mdb.deleteRow(idx);
            if (result == 0) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });
        
    }

    @Before
    public void setUp() throws Exception{
        init();

        Spark.awaitInitialization();

        mdb.insertRow("Testing my proposals", 0);
        mdb.insertRow("This is a great proposal", 10);

    }

    @org.junit.Test
    public void testingGet(){
        try{
            URL url = new URL("http://localhost:4567/proposals");  
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            //String jsonFull = gson.toJson(new StructuredResponse("ok", null, mdb.selectAll()));
            String jsonFull = gson.toJson(IOUtils.toString(connection.getInputStream()));
            //now iterate through mdb and check that each item is in the json response

            //System.out.println("FULL JSON:: " + jsonFull);

            //iterate through the datastructure in the mockdb object and check that each item is included in the JSON response
            for (DataRow dr : mdb.simedDatabase){
                //System.out.println("TESTING:: " + "\\\"message\\\":\\\""+dr.message+"\\\",\\\"numLikes\\\":"+dr.numLikes);
                assertTrue(jsonFull.contains("\\\"message\\\":\\\""+dr.message+"\\\",\\\"numLikes\\\":"+dr.numLikes));
            }
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testingPut() {
        try{
            //like message with id 1
            URL url = new URL("http://localhost:4567/proposals/1/true"); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            //check that it is actually liked
            int actualLikes = mdb.selectOne(mdb.findIndex(1)).numLikes; //get the like count for the post of the given id

            assertTrue(actualLikes == 1);

            //now test unliking

            url = new URL("http://localhost:4567/proposals/1/false");  
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");

            responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            //check that it is actually liked
            actualLikes = mdb.selectOne(mdb.findIndex(1)).numLikes; //get the like count for the post of the given id

            assertTrue(actualLikes == 0);


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testingPost() {
        try{
            URL url = new URL("http://localhost:4567/proposals");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json"); //tells what the body format is

            //allow inputting/outputting for the connection
            connection.setDoInput(true);
            connection.setDoOutput(true);

            String jsoninput = "{\"proposalText\": \"Sample ProposalTextmessage\", \"numLikes\": 0}";
            // write the bytes of the JSON input to the conneciton's output stream
            try(OutputStream os = connection.getOutputStream()){
                byte[] input = jsoninput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            //we know that this added item should have an id of 3, so lets grab it and make sure it exists
            DataRow returnedRow = mdb.selectOne(mdb.findIndex(3));
            assertTrue(!(returnedRow == null));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testingDelete(){
        try{
            URL url = new URL("http://localhost:4567/proposals/1"); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            //make sure the row does not exist.
            //call findIndex(1) and make sure that it returns -1
            assertEquals(-1,mdb.findIndex(1));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}