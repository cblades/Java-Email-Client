package edu.wcu.cs.cs363.team3.project02;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of AbstractTableModel that gives a JTable access to the
 * EmailContacts retrieved via DataStore.
 *
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 * @version 6/4/2010
 */
public class ContactTableModel extends AbstractTableModel {
    /**
     * Array of strings to use as column headers in a JTable
     */
    private String[] columnNames = EmailContact.names();
    

    /**
     * Returns the column name for the given column.
     * @param column index of the column whose name this method will return
     * @return the name of the indicated column
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Sets the value of the cell at the given row and column.
     *
     * @param aValue object to set the value of the cell to
     * @param row    row coordinate of the cell to update
     * @param col    column coordinate of the cell to update
     */
    public void setValueAt(Object aValue, int row, int col) {
        ArrayList<EmailContact> contacts = new ArrayList<EmailContact>(
                                             DataStore.get().getContacts());
        EmailContact rowContact = contacts.get(row);
        String[] values = rowContact.toArray();
        values[col] = aValue.toString();
        fireTableCellUpdated(row, col);
    }

    /**
     * Removes the given contact from the underlying collection of 
     * contacts found in DataStore, and thus from any table using this
     * ContactTableModel.
     *
     * @param contact  the contact to remove.
     */
    public void removeContact(EmailContact contact) {
        DataStore.get().getContacts().remove(contact);
        fireTableDataChanged();
    }

    /**
     * Adds a contact to the underlying collection of contacts found in 
     * DataStore.
     *
     * @param contact the contact to add
     */
    public void addContact(EmailContact contact) {
        Collection<EmailContact> contacts = DataStore.get().getContacts();
        contacts.add(contact);

        fireTableRowsInserted(contacts.size() - 1, contacts.size() - 1);
    }

    /**
     * Returns the value at the specified cell.
     *
     * @param row the row coordinate of the cell
     * @param col the column coordinate of the cell
     */
    public Object getValueAt(int row, int col) {
        ArrayList<EmailContact> contacts = new ArrayList<EmailContact>(
                                             DataStore.get().getContacts());
        EmailContact rowContact = contacts.get(row);
        return rowContact.toArray()[col];
    }
       
    /**
     * Returns the number of columns represented by this ContactTableModel.
     *
     * @return the number of columns
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the number of rows in this model.
     *
     * @return the number of rows
     */
    public int getRowCount() {
        return DataStore.get().getContacts().size();
    }
}
