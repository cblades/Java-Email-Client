package edu.wcu.cs.cs363.team3.project02;

/**
 * Verifies that a string matches the pattern of a URL <b>without</b>
 * protocol prefix.
 *
 * @author Chris Blades
 * @author Don Coffin
 * @author Benny Reese
 *
 * @version 7/4/2010
 */
public class URLVerifier implements FieldVerifier {
    /**
     * Regex defining a url.  format:
     * "localhost" OR "www.google.com"
     */
    private String expression = "[A-z0-9\\-_]+(\\.[A-z0-9\\-_]+)*";
    
    /**
     * Returns true if the given text matches the pattern of a url.
     *
     * @param text the string to check
     * @return true if text is a url
     */
    public boolean verify(String text) {    
        return text.matches(expression);
    }

    /**
     * Returns a URL that would validate according to this Verifier.
     *
     * @return an example URL
     */
    public String example() {
        return "www.google.com";
    }
}
