package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JFrame;

/**
 * Tests functionality of MainFrame.  Since MainFrame is a gui component, 
 * testing must be done manually by the user.
 *
 * @author Chris Blades
 * @author Don Coffin
 * @author Benny Reese
 *
 * @version 28/3/2010
 */
public class MainFrameTest {
    /**
     * Creates a new MainFrame that the user can test.
     * 
     * @param args arguments passed from command line, not used here
     */
    public static void main(String[] args) {
        JFrame window = new MainFrame("Mail app");
        window.setSize(200, 500);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
