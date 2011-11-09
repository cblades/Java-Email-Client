package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JFrame;


/**
 * Tests the ContactEditingFrame.  Since this is a purely GUI element, 
 * test has to be done manually.
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 *
 * @version 27/3/2010
 */
public class ContactEditingFrameTest {
    
    /**
     * Creates a new ContactEditingFrame containing a contact with
     * random data so that user can test GUI.
     *
     * @param args arguments passed at command line, not used here.
     */
    public static void main(String[] args) {
        EmailContact contact = new EmailContact("Chris Blades",
                                                "WCU",
                                                "555-555-5555",
                                                "chris@test.com");
        
        MainFrame window = new MainFrame("background");
        window.setSize(200, 500);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        new ContactEditingFrame(window, contact);
    }
}
