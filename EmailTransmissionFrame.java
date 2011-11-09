package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;


/**
 * A JFrame that encapsulates the functionality of an email composition
 * window.
 *
 * @author Dr. Dalton
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 *
 * @version 4-4-10
 */
public class EmailTransmissionFrame extends JDialog {
    /** A button used to send the message. */
    private JButton sendButton;

    /** A button used to cancel the message. */
    private JButton cancelButton;

    /** A field to contain the destination address. */
    private ValidatedField toField;

    /** A field to contain the source address. */
    private ValidatedField fromField;

    /** A field to contain the subject. */
    private ValidatedField subjectField;

    /** A text area to contain the body of the message. */
    private ValidatedArea messageBody;

    /** The number of header fields. */
    private final static int NUM_FIELDS = 3;


    /**
     * An action listener that will respond to the user pressing the send
     * button.  This class plays the role of an observer in the Observer
     * pattern.
     */
    private class SendButtonListener implements ActionListener {
        /**
         * This method is invoked with the send button is pressed.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Properties props = System.getProperties();
                props.put("mail.smtp.host", 
                    DataStore.get().getSystemConfiguration().getSMTPServer());

                Session session = Session.getDefaultInstance(props);
                Message msg     = new MimeMessage(session);

                msg.setFrom(new InternetAddress(fromField.getText()));
                msg.setRecipients(Message.RecipientType.TO, 
                        InternetAddress.parse(toField.getText()));
                msg.setSubject(subjectField.getText());
                msg.setText(messageBody.getText());

                Transport.send(msg);
                EmailTransmissionFrame.this.setVisible(false);
            } catch (MessagingException ex) {
                JOptionPane.showMessageDialog(EmailTransmissionFrame.this, 
                    ex.getMessage(), "JavaMail Exception", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } 
        }
    }


    /**
     * An action listener that will respond to the user pressing the cancel
     * button.  This class plays the role of an observer in the Observer
     * pattern.
     */
    private class CancelButtonListener implements ActionListener {
        /**
         * This method is invoked with the cancel button is pressed.
         */
        public void actionPerformed(ActionEvent e) {
            EmailTransmissionFrame.this.setVisible(false);
        }
    }


    /**
     * A class that mediates communication between the text fields of the
     * email frame and the send button.  This class represents an instance
     * of the Mediator pattern.  It is used to make sure that all the input
     * fields are valid before enabling the send button.
     * <p/>
     * The class also plays the role of an observer in the Observer pattern.
     * Instances of this class observe one or more documents associated with
     * the text fields/areas.  When changes are made to those documents (by
     * typing or removing text from the corresponding fields), the object
     * will get notified.
     */
    private class SendButtonMediator implements DocumentListener {

        /**
         * Construct a new <code>SendButtonMediator</code>.  This will
         * set the state of the send button based on the initial state
         * of the fields.
         */
        public SendButtonMediator() {
            setState();
        }

        /**
         * Text has been inserted into a document, update the state of the
         * send button based on the new state of the fields.
         */
        public void insertUpdate(DocumentEvent e) {
            setState();
        }

        /**
         * Text has been removed from a document, update the state of the
         * send button based on the new state of the fields.
         */
        public void removeUpdate(DocumentEvent e) {
            setState();
        }

        /**
         * A helper method that sets the state of the send button based on the
         * states of the toField, fromField, subjectField, and messageBody.
         */
        private void setState() {
            sendButton.setEnabled(   
                                 toField.isValid()
                              && fromField.isValid()
                              && subjectField.isValid()
                              && messageBody.isValid());
        }

        /**
         * This method is part of the <code>DocumentListener</code> interface.
         * It is not used in this class.
         */
        public void changedUpdate(DocumentEvent e) {
            /* Ingore this one */
        }
    }


    /**
     * Construct a new mail window with the specified title.
     *
     * @param title The title of the new mail window.
     */
    public EmailTransmissionFrame(Frame owner, String title) {
        super(owner, title, /*modal: yes*/ true);

        this.setLayout(new BorderLayout());

        /*
         * Divide and conquer -- break the GUI into smaller pieces and have
         * helper methods build and return those pieces.
         */
        this.add(buildHeaderComponent(),  BorderLayout.NORTH);
        this.add(buildMessageComponent(), BorderLayout.CENTER);
        this.add(buildButtonComponent(),  BorderLayout.SOUTH);

        // Set up this listeners for all of our components
        this.addListeners();
        this.setSize(400, 300);
    }

    /**
     * Sets the address to which this email will be sent.
     *
     * @param toAddres address to send to.
     */
    public void setToAddress(String toAddress) {
        toField.setText(toAddress);
    }

    /**
     * Builds the top (NORTH) portion of the frame, which contains the header
     * fields.  This portion is divided into two portions, the left and the
     * right.
     *
     * @return a component that contains the top portion of the overall frame.
     */
    private JComponent buildHeaderComponent() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(buildHeaderLabels(), BorderLayout.WEST);
        panel.add(buildHeaderFields(), BorderLayout.CENTER);

        return panel;
    }


    /**
     * Builds a component that contains the labels for the header fields.
     *
     * @return a component that contains the labels for the header fields.
     */
    private JComponent buildHeaderLabels() {
        int    rows  = NUM_FIELDS;
        int    cols  = 1;
        JPanel panel = new JPanel(new GridLayout(rows, cols));

        panel.add(new JLabel("To:"));
        panel.add(new JLabel("From:"));
        panel.add(new JLabel("Subject:"));

        return panel;
    }


    /**
     * Builds a component that contains the header fields.
     *
     * @return a component that contains the header fields.
     */
    private JComponent buildHeaderFields() {
        int    rows  = NUM_FIELDS;
        int    cols  = 1;
        JPanel panel = new JPanel(new GridLayout(rows, cols));

        toField      = new ValidatedField(new EmailTextVerifier());

        fromField    = new ValidatedField(new EmailTextVerifier());
        fromField.setEditable(false);
        fromField.setText(
        DataStore.get().getSystemConfiguration().getEmail());

        subjectField = new ValidatedField(new NonNullTextVerifier());

        panel.add(toField);
        panel.add(fromField);
        panel.add(subjectField);

        return panel;
    }


    /**
     * Builds a component that contains the text area that represents the
     * message body.
     *
     * @return a component that contains the text area that represents the
     *         message body.
     */
    private JComponent buildMessageComponent() {
        messageBody = new ValidatedArea(new NonNullTextVerifier());
        messageBody.setLineWrap(true);
        messageBody.setWrapStyleWord(true);
        // Decorate the text area with a scroll pane.
        return new JScrollPane(messageBody);
    }


    /**
     * Builds a component that contains the send and cancel buttons.
     *
     * @return a component that contains the send and cancel buttons.
     */
    private JComponent buildButtonComponent() {
        JPanel panel = new JPanel(new FlowLayout());

        sendButton   = new JButton("Send");
        
        cancelButton = new JButton("Cancel");

        panel.add(sendButton);
        panel.add(cancelButton);

        return panel;
    }


    /**
     * Adds appropriate listeners to any components.
     */
    private void addListeners() {
        sendButton.addActionListener(new SendButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());

        DocumentListener mediator = new SendButtonMediator();

        toField.getDocument().addDocumentListener(mediator);
        subjectField.getDocument().addDocumentListener(mediator);
        messageBody.getDocument().addDocumentListener(mediator);
    }

    /**
     *  A unit tester for this class.
     *
     *  @param args unused.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Email Test");
        frame.setSize(300,300);
        EmailTransmissionFrame etf = new EmailTransmissionFrame(frame, "Test");
        etf.fromField.setEditable(true);
        etf.setVisible(true);

    }
}
