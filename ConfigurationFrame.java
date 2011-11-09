package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * A JDialog that encapsulates the functionality of a configuration window
 * to set basic information such as User's Primary E-mail address and
 * outgoing SMTP server.
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 * @version 7/4/2010
 */
public class ConfigurationFrame extends JDialog {
    /** The number of fields in the header.  (usersEmail and SMTP Serv.) */
    private final static int NUM_FIELDS = 2;

    /** A button used to save the settings. */
    private JButton saveButton;
    
    /** A button used to cancel any changes. */
    private JButton cancelButton;

    /** The text field used to get the entered e-mail address. */
    private ValidatedField usersEmail;

    /** The text field used to get the entered outgoing SMTP server. */
    private ValidatedField outSMTPServer;
    

    /**
     * Constructs the configuration window.  Sets it to modal so that
     * no other window can be used unitl it is closed.
     */
    public ConfigurationFrame(Frame parent) {
        super (parent, "Configuration Options", true);

        this.setLayout(new BorderLayout());
        this.add(headerComponent(), BorderLayout.NORTH);
        this.add(buttonComponent(), BorderLayout.SOUTH);

        this.addListeners();
    }

    /**
     * Creates the top part of the configuration window.  It uses
     * the west (left) side of the window for the labels and the 
     * center (in this case the right side) side of the winodw to
     * contain the text fields that will be used to enter data.
     *
     * @return A JComponent that is the north (top) part of the window.
     */
    private JComponent headerComponent() {
        JPanel header = new JPanel(new BorderLayout());

        header.add(headerLabels(), BorderLayout.WEST);
        header.add(headerFields(), BorderLayout.CENTER);

        return header;
    }
    
    /**
     * Creates the labels for the header fields.
     *
     * @return A JComponent with the labels for the fields.
     */
    private JComponent headerLabels() {
        int rows = NUM_FIELDS;
        int cols = 1;
        JPanel labelsPanel = new JPanel(new GridLayout(rows, cols));

        labelsPanel.add(new JLabel("Primary Email Address:  "));
        labelsPanel.add(new JLabel("Outgoing SMTP Server:  "));

        return labelsPanel;
    }
    
    /**
     * Creates the text fields that are used to enter data.  These
     * will go in the north (top) part of the window.
     *
     * @return A JComponent containing the text fields used to enter
     * data.
     */
    private JComponent headerFields() {
        int rows = NUM_FIELDS;
        int cols = 1;
        SystemConfiguration config = DataStore.get().getSystemConfiguration();

        JPanel fieldsPanel = new JPanel(new GridLayout(rows, cols));

        usersEmail = new ValidatedField(new EmailTextVerifier());
        usersEmail.setText(config.getEmail());

        outSMTPServer = new ValidatedField(new URLVerifier());
        outSMTPServer.setText(config.getSMTPServer());

        fieldsPanel.add(usersEmail);
        fieldsPanel.add(outSMTPServer);

        return fieldsPanel;
    }

    /**
     * Creates the buttons that will go in the south (bottom) part of
     * the configuration window.
     *
     * @return A JComponent that contains the buttons for saving and
     * canceling.
     */
    private JComponent buttonComponent() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        saveButton = new JButton("Save");

        cancelButton = new JButton("Cancel");

        saveButton.setEnabled(false);;

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        return buttonsPanel;
    }
    
    /**
     * Uses the ActionListener class to know when the save button is
     * pressed
     */
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SystemConfiguration config = 
                                DataStore.get().getSystemConfiguration();
        
            config.setEmail(usersEmail.getText());
            config.setSMTPServer(outSMTPServer.getText());
            
            ConfigurationFrame.this.setVisible(false);
        }
    }

    /**
     * Uses the ActionListener class to know when the cancel button is
     * pressed.  Sets the Configuration Frame to not visible.
     */
    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ConfigurationFrame.this.setVisible(false);
        }
    }
    
    /**
     * Attaches the appropriate listeners to the subjects that
     * are to be monitored.
     */
    private void addListeners() {
        saveButton.addActionListener(new SaveButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());

        DocumentListener mediator = new SaveButtonMediator();

        usersEmail.getDocument().addDocumentListener(mediator);
        outSMTPServer.getDocument().addDocumentListener(mediator);
    }

    /**
     * Used to monitor when changes are made to a particular subjet
     */
    private class SaveButtonMediator implements DocumentListener {
        /**
         * Constructs a new SendButotnMediator and calls the
         * setSaveButtonState() method.  This makes the initial state
         * of the button based on the initial state of the entity being
         * monitored.
         */
        public SaveButtonMediator() {
            setSaveButtonState();
        }
        
        /**
         * Whenever something is inserted into the entity being monitored,
         * we want to base the state of the save button on the new state
         * of the entity.
         */
        public void insertUpdate(DocumentEvent e) {
            setSaveButtonState();
        }

        /**
         * Whenever something is removed from the entity being monitored,
         * we want to base the state of the save button on the new state
         * of the entity.
         */
        public void removeUpdate(DocumentEvent e) {
            setSaveButtonState();
        }
        
        /**
         * Used to control whether the save button is enabled or not depending
         * on the validity of the text entered in the text fields.  It uses
         * regular expressions to check the validity of the entered data.
         */
        private void setSaveButtonState() {

           saveButton.setEnabled(usersEmail.isValid() 
                            && outSMTPServer.isValid());
        
        }

        /**
         * This is method is part of the DocumentListener interface and
         * thus must be part of the code.  However, since we do not need
         * it, we have the method do nothing.
         */
        public void changedUpdate(DocumentEvent e) {
            /* Not needed for this class */
        }
    }
}
