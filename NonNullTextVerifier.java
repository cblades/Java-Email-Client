package edu.wcu.cs.cs363.team3.project02;

/**
 *  This class will check to see if a string is null or not.
 *
 *  @author Benny Reese
 *  @author Don Coffin
 *  @author Chris Blades
 *
 *  @version 4-4-10
 */
public class NonNullTextVerifier implements FieldVerifier {

    /**
     *  Method exposed to check if the string is not null.
     *
     *  @param text the string to check for non-null-ness
     *
     *  @return true if text is not null or just whitespace.
     */
    public boolean verify(String text) {
        return ! text.trim().equals("");
    }

    /**
     * Returns an example of a pattern that would validate according to this
     * Verifier.
     *
     * @return an example of a non-null string
     */
    public String example() {
        return "Foo bar";
    }
}
