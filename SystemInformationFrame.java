package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

/**
 * Frame displaying information about the email address and smtp server used
 * by this program and also a description of the program.
 *
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 * @version 6/4/2010
 */
public class SystemInformationFrame extends JDialog {
    
    /**
     * Creates a new SystemInformationFrame with the given parent frame
     */
    public SystemInformationFrame(JFrame parent) {
        super(parent, "System Informaiton", /*is modal: yes*/ true);
        this.buildWindow();
    }
    
    /**
     * Constructs and lays out the various components contained within this
     * frame.
     */
    private void buildWindow() {
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(createConfigInfoPanel());
        container.add(createAboutPanel());
    }

    /**
     * Construts and lays out the panel containing information about the
     * email address and smtp server used by this program.
     *
     * @return a panel with system configuration info
     */
    private JPanel createConfigInfoPanel() {
        SystemConfiguration configuration = 
                    DataStore.get().getSystemConfiguration();
        JLabel smtpLabel = new JLabel("SMTP Server:");  
        JLabel smtpServer = new JLabel(configuration.getSMTPServer());
        JLabel emailLabel = new JLabel("Email Address:"); 
        JLabel emailAddress = new JLabel(configuration.getEmail());
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        left.add(smtpLabel, BorderLayout.NORTH);
        left.add(emailLabel, BorderLayout.SOUTH);
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.add(smtpServer, BorderLayout.NORTH);
        right.add(emailAddress, BorderLayout.SOUTH);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(left);
        panel.add(right);
        Dimension size = panel.getPreferredSize();
        size.width = Short.MAX_VALUE;
        panel.setMaximumSize(size);
        return panel;
    }

    /**
     * Constructs and lays out a panel containing a description of this 
     * program.
     *
     * @return a panel containing a description of this program
     */
    private JPanel createAboutPanel() {
        JPanel panel = new JPanel();
        JLabel aboutLabel = new JLabel("About");
        JTextArea aboutText = new JTextArea();
        aboutText.setEditable(false);
        aboutText.setText("Java Email Client.\nThis is an application to" +
            " send emails and maintain a list of contacts. It was written by" +
            " Don Coffin, Benny Reese, and Chris Blades. Parts of the design " +
            "were created by Dr. Andrew Dalton");
        aboutText.setWrapStyleWord(true);
        aboutText.setLineWrap(true);
        panel.setLayout(new BorderLayout());
        panel.add(aboutLabel, BorderLayout.WEST);
        panel.add(aboutText, BorderLayout.CENTER);
        return panel;
    }
}
   
