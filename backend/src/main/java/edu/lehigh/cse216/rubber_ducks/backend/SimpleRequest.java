package edu.lehigh.cse216.rubber_ducks.backend;

/**
 * SimpleRequest provides a format for clients to present proposals and likes 
 * to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequest {
    /**
     * The title being provided by the client.
     */
    public String proposalText;

    /**
     * The liked being provided by the client.
     */
    public int numLikes;
}