package edu.wcu.cs.cs363.team3.project02;

import java.io.Serializable;

/**
 * Data structure for storing details about a contact.
 *
 * @author Benny Reese
 * @author Chris Blades
 * @author Don Coffin
 *
 * @version 6/4/2010
 */
public class EmailContact implements Serializable {

    /** the contact's name */
    private String name;
    /** the contact's postal address */
    private String address;
    /** the contact's phone number */
    private String phoneNumber;
    /** the contact's email address */
    private String emailAddress;

    /**
     * Constructor that initializes all of the fields to supplied values.
     *
     * @param name the name of the contact
     * @param address the address of the contact
     * @param phoneNumber the phone number of the contact
     * @param emailAddress the email address of the contact
     */
    public EmailContact(String name, String address, String phoneNumber, 
                        String emailAddress) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Default empty constructor that passes empty strings for the values of 
     * the fields.
     */
    public EmailContact() {
        // default behavior, fill with empty strings.
        this("", "", "", "");
    }

    /**
     * Accessor method for the field name
     * @return the name of the contact
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor method for the field address
     * @return the address of the contact
     */
    public String getAddress() {
        return address;
    }

    /**
     * Accessor method for the field phoneNumber
     * @return the phone number of the contact
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Accessor method for the field emailAddress
     * @return the email address of the contact
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Mutator method for the field name
     * @param name the name of the contact
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mutator method for the field address
     * @param address the address of the contact
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Mutator method for the field emailAddress
     * @param email the email address of the contact
     */
    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    /**
     * Mutator method for the field phoneNumber
     * @param number the phone number of the contact
     */
    public void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }


    /**
     * Returns this objects state values in array form.
     *
     * @return an array containing the contacts name, address, number, and
     *          email
     */
    public String[] toArray() {
        return new String[]{name, address, phoneNumber, emailAddress};
    }

    /**
     * Sets this objects state values to those contained in the given
     * array.
     *
     * @param values an array containing name, address, number, and email for
     *               this contact.
     */
    public void setValues(String[] values) {
        this.name         = values[0];
        this.address      = values[1];
        this.phoneNumber  = values[2];
        this.emailAddress = values[3];
    }

    /**
     * Return an array of names for the values contained for a contact.
     * <b>Returns names in the same order that toArray() will return valuse.</b>
     * 
     * @return an array of names to of the values stored for contacts
     */
    public static String[] names() {
        return new String[]{"Name", "Address", 
                            "Phone Number", "Email Address"};
    }

    /**
     * Returns this object in string form
     *
     * @return a string version of this object.
     */
    public String toString() {
        return name + " " + 
               emailAddress + " " + 
               address + " " + 
               phoneNumber;
    }
}

