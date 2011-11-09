package edu.wcu.cs.cs363.team3.project02;

/**
 * A class to check if a string matches the form of a name
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 *
 * @version 4-4-10
 */
public class NameTextVerifier implements FieldVerifier {
    
    /**
     * Regex definition of a name
     */
    private static final String expression =
                            "[a-zA-Z\\-]+[\\a-zA-Z\\-]*";
    /**
     * Returns wether or not the given String matches the pattern for a name.
     *
     * @param name the String to check
     * @return true if the given String is a name
     */
    public boolean verify(String name) {
        return name.matches(expression);
    }

    /**
     * Returns an esample of a name that would validate according to this
     * Verifier.
     *
     * @return an example name
     */
    public String example() {
        return "John Smith";
    }
}
