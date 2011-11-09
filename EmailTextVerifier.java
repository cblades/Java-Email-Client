package edu.wcu.cs.cs363.team3.project02;

import java.util.Scanner;

/**
 *  A class to check if a string matches the form of an email.
 *
 *  @author Chris Blades
 *  @author Don Coffin
 *  @author Benny Reese
 *
 *  @version 4-4-10
 */
public class EmailTextVerifier implements FieldVerifier {

    /** 
     * A string that is a regular expression to match for an email address. 
     */
    private static final String expression = 
                "[A-z0-9_.-]+@[A-z0-9_-]+(\\.[A-z0-9_-]+)*";
    /**
     *  Method exposed to check if the string matches the form of an email:
     *      something(AT)something.something
     *
     *  @param text the string to match against the email pattern
     *
     */
    public boolean verify(String text) {
        return text.matches(expression);
    }
    
    /**
     * Returns an example Email Address that would validate according to this
     * verifier
     *
     * @return an example email address
     */
    public String example() {
        return "John_Smith@example.com";
    }

    /**
     *  A simple method of testing this class.
     *  Passes tests. Yes we should have made a JUnit test for this. This was
     *  easier and saved time.
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        EmailTextVerifier etv = new EmailTextVerifier();
        while (true) {
            System.out.println(etv.verify(s.nextLine()));
        }
    }

}
