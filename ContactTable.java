package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JTable;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * ContactTable represents a JTable displaying info contained within
 * EmailContact objects.
 *
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 * @version 6/4/2010
 */
public class ContactTable extends JTable {

    /**
     * Creates a new instance of ContactTable with a ContactTableModel.
     */
    public ContactTable() {
        super(new ContactTableModel());
    
    }

    /**
     * Returns the contact represented by the selected row.
     *
     * @return the <b>first</b> selected contact <b>OR</b> null if no contact is
     *         currently selected
     */
    public EmailContact getSelectedContact() {
        // the selected row will also be an index into contacts
        int rowSelected = this.getSelectedRow();

        // if no contact is selected, return -1
        if (rowSelected == -1) {
            return null;
        // else return the contact at the row index
        } else {
            ArrayList<EmailContact> contacts = new ArrayList<EmailContact>(
                                DataStore.get().getContacts());
            return contacts.get(rowSelected);
        }
    }
    
    /**
     * Adds a contact to the underlying ContactTableModel.
     *
     * @param contact the contact to add to this table.
     */
    public void addContact(EmailContact contact) {
       ContactTableModel tableModel = (ContactTableModel)this.getModel();
       tableModel.addContact(contact);
    }

    /**
     * Removes a contact from the underlying ContactTableModel.
     *
     * @param contact the contact to remove from this table.
     */
    public void removeContact(EmailContact contact) {
       ContactTableModel tableModel = (ContactTableModel)this.getModel();
       tableModel.removeContact(contact);
    }
}

