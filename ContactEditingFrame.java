package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JPanel;
import javax.swing.JPanel;;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.WindowConstants;

/**
 * Frame that provides a way for the user to edit the values of an
 * EmailContact.
 *
 * 
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 * @version 6/4/2010
 */
public class ContactEditingFrame extends JDialog {
    /**Contact that is being edited*/
    private EmailContact contact;

    /**
     * The MainFrame which created this ContactEditingFrame, used to notify
     * parent that a contact has been updated
     */
    private MainFrame parent;

    /**
     * an editable field in which the user can enter the contact's name
     */
    private ValidatedField name;

    /**
     * an editable field in which the user can enter the contact's address
     */
    private ValidatedField address;

    /**
     * an editable field in which the user can enter the contact's phone number
     */
    private ValidatedField phoneNumber;

    /**
     * an editable field in which the user can enter the contact's email
     */
    private ValidatedField emailAddress;

    /**
     * Button that, when clicked, will save the updated values of the contact
     */
    private JButton saveButton;

    /**
     * Button that cancels the edit and discards all changes
     */
    private JButton cancelButton;

    /**
     * Creates a new ContactEditingFrame with the given parent frame and
     * EmailContact to edit.
     * 
     * @param parent the frame creating this ContactEditingFrame
     * @param contact the contact to edit
     */
    public ContactEditingFrame(MainFrame parent, EmailContact contact) {
        super(parent, "Edit Contact", true);

        this.parent = parent;       
        this.contact = contact;

        name         = new ValidatedField(new NameTextVerifier());
        address      = new ValidatedField(new AddressTextVerifier());
        phoneNumber  = new ValidatedField(new PhoneTextVerifier());
        emailAddress = new ValidatedField(new EmailTextVerifier());

        saveButton =   new JButton("Save");
        cancelButton = new JButton("Cancel");

        addListeners();

        buildWindow();
        initializeFields();

        this.setSize(300, 200);
        // this.setResizable(false);
        this.setVisible(true);
        this.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowLostFocus(WindowEvent e) {
                cancelling();
                }
                });
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
     * To be called when the window is closing without saving.
     * If the contact was empty (i.e. they clicked add) then we delete it.
     */
    private void cancelling() {
        if (contact.toString().trim().equals("")) {
            DataStore.get().getContacts().remove(contact);
            parent.updateContacts();
        }
        ContactEditingFrame.this.setVisible(false);
    }

    /**
     * Assembles and lays out the various components in this Dialog
     */
    private void buildWindow() {
        setLayout(new BorderLayout());

        //
        // builds text fields panel
        //
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(/*rows*/ 5, /*cols*/ 2));

        fieldsPanel.add(new JLabel("Name:"));
        fieldsPanel.add(name);
        name.setToolTipText("Contact's name");

        fieldsPanel.add(new JLabel("Address:"));
        fieldsPanel.add(address);
        address.setToolTipText("Contact's postal address");

        fieldsPanel.add(new JLabel("Phone Number:"));
        fieldsPanel.add(phoneNumber);
        phoneNumber.setToolTipText("Contact's phone number");

        fieldsPanel.add(new JLabel("Email Address:"));
        fieldsPanel.add(emailAddress);
        emailAddress.setToolTipText("Contact's e-mail address");

        add(fieldsPanel, BorderLayout.CENTER);

        //
        // builds buttons panel
        //
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(/*rows*/ 1, /*cols*/ 2));
        buttonPanel.add(saveButton);
        saveButton.setToolTipText("Save changes to contact");

        buttonPanel.add(cancelButton);
        cancelButton.setToolTipText("Discard any changes made to contact");

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Initizlizes the text fields to the values initially contained in the
     * contact.
     */
    private void initializeFields() {
        name.setText(contact.getName());
        address.setText(contact.getAddress());
        phoneNumber.setText(contact.getPhoneNumber());
        emailAddress.setText(contact.getEmailAddress());
    }

    /**
     * Adds ActionListeners to the save and cancel buttons and istantiates
     * a mediator for the save button.
     */
    private void addListeners() {
        saveButton.addActionListener(new SaveButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());

        DocumentListener mediator = new SaveButtonMediator();

        name.getDocument().addDocumentListener(mediator);
        address.getDocument().addDocumentListener(mediator);
        phoneNumber.getDocument().addDocumentListener(mediator);
        emailAddress.getDocument().addDocumentListener(mediator);
    }

    /**
     * Intercepts events when the save button is clicked, saves updated
     * contact info, and removes the ContactEditingFrame dialog.
     *
     */
    private class SaveButtonListener implements ActionListener {
        /**
         * Is called when the save button is clicked, update contact and
         * close dialog.
         *
         * @param e the event that triggered this call
         */
        public void actionPerformed(ActionEvent e) {
            contact.setName(name.getText());
            contact.setAddress(address.getText());
            contact.setPhoneNumber(phoneNumber.getText());
            contact.setEmailAddress(emailAddress.getText());

            parent.updateContacts();
            ContactEditingFrame.this.setVisible(false);
            ContactEditingFrame.this.dispose();
        }
    }

    /**
     * Intercepts events when the cancel button is clicked, removes the
     * ContactEditingFrame dialog without saving changes to contact.
     *
     */
    private class CancelButtonListener implements ActionListener {
        /**
         * Is called when the cancel button is clicked.
         *
         * @param e the event that triggered this call
         */
        public void actionPerformed(ActionEvent e) {
            cancelling();
        }
    }


    /**
     * Modifies the state of the save button depending on the validity of
     * the information in the value text fields.
     */
    private class SaveButtonMediator implements DocumentListener {
        /**
         * Creates a new instace of SaveButtonMediator and sets the inital
         * state of the save button.
         */
        public SaveButtonMediator() {
            setSaveButtonState();
        }

        /**
         * Calls setSaveButtonState.  No interest in generating event.
         * @param e the event that triggered this call
         */
        public void insertUpdate(DocumentEvent e) {
            setSaveButtonState();
        }

        /**
         * Calls setSaveButtonState.  No interest in generating event.
         * @param e the event that triggered this call
         */
        public void removeUpdate(DocumentEvent e) {
            setSaveButtonState();
        }

        /**
         * Sets the save button state.  Save button is enabled if all fields
         * validate and disabled otherwise.
         */
        private void setSaveButtonState() {

            saveButton.setEnabled(
                       name.isValid()
                    && address.isValid()
                    && phoneNumber.isValid()
                    && emailAddress.isValid()); 
        }

        /**
         * Calls setSaveButtonState.  No interest in generating event.
         * @param e the event that triggered this call
         */
        public void changedUpdate(DocumentEvent e) {
        }
    }
}
