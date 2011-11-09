package edu.wcu.cs.cs363.team3.project02;

/**
 * A class to check if a string matches the form of a phone number.
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 *
 * @version 4-6-10
 */
public class PhoneTextVerifier implements FieldVerifier {
    
    /**
     * Regex definition of a phonenumber
     */
    private static final String expression = 
        "([0-9]{3}-)?[0-9]{3}-[0-9]{4}";

    /**
     * Returns true if the given String matches the pattern for a phone number.
     *
     * @param phoneNumber the String to check
     * @return true if the given string is a phone number
     */
    public boolean verify(String phoneNumber) {
        return phoneNumber.matches(expression);
    }

    /**
     * Returns an example phone number that would validate according to
     * this Verfifier.
     * 
     * @return ane example phone number
     */
    public String example() {
        return "828-555-1234";
    }
}
