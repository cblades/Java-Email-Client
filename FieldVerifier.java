package edu.wcu.cs.cs363.team3.project02;

/**
 * Interface for a generic text verifier to check if a string matches a pattern
 *
 * @author Benny Reese
 *
 * @version 4-4-10
 */
public interface FieldVerifier {
   
    /**
     *  Method exposed to check if the string matches the pattern.
     *
     *  @param text the string to verify
     *
     *  @return true if text matches the pattern, false otherwise.
     */
    public boolean verify(String text);

    /**
     * Returns an example of the pattern this verifier is checking for.
     *
     * @return a sample String that would valiidate
     */
    public String example();
}
