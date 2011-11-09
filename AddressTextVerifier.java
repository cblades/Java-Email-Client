package edu.wcu.cs.cs363.team3.project02;

/**
 * A class to check if a string matches the form of an address.
 *
 * Valid addresses follow the form of:
 *      100 Road Name Place, City, NC 11111
 *
 * with no requirements or limits on the number of digits in the street address
 * or zip code.
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 *
 * @version 4-6-10
 */
public class AddressTextVerifier implements FieldVerifier {
    /**Regular expression defining an address*/
    private static final String expression = 
        "[0-9]+ [ A-z.]+, [ A-z]+, [A-Z]{2} [0-9]+";

    /**
     * Returns true if the given string matches a regular expression 
     * defining an address.
     *
     * @param address String to check against expression.
     * @return true if the given string is an address.
     */
    public boolean verify(String address) {
        return address.matches(expression);
    }

    /**
     * Returns a String with a sample address that this Verifier would
     * verify.
     *
     * @return an example address
     */
    public String example() {
        return "123 Example Rd., Mulberry, NC, 12345";
    }
}
