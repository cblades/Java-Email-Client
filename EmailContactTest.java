package edu.wcu.cs.cs363.team3.project02;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testsuite for the EmailContact class.
 *
 * @author Benny Reese
 * @author Chris Blades
 * @author Don Coffin
 *
 * @version 4-2-10
 */
public class EmailContactTest {

    /**
     * An empty email contact to test the default constructor and the setter
     * methods.
     */
    private EmailContact emptyContact;

    /**
     * Run before each test, sets up emptyContact to be an empty EmailContact.
     */
    @Before
    public void setUp() {
        emptyContact = new EmailContact();
    }

    /**
     * Tests the default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        Assert.assertEquals(
            "EmailContact default constructor should've been empty",
            emptyContact.toString().trim(), 
            "");
    }

    /**
     * Tests the accessor and mutator methods of EmailContact
     */
    @Test
    public void testSettersAndGetters() {
        String email = "test@test.com";
        emptyContact.setEmailAddress(email);
        Assert.assertEquals(
            "EmailAddress setter failed.",
            email,
            emptyContact.getEmailAddress());
        Assert.assertEquals(
            "EmailAddress setter modified more than just one field.",
            email,
            emptyContact.toString().trim());

        String name = "Foo Bar";
        emptyContact.setName(name);
            
        Assert.assertEquals(
            "Name setter failed.",
            name,
            emptyContact.getName());
            
        Assert.assertEquals(
            "Name setter modified more than just one field.",
            name + " " + email,
            emptyContact.toString().trim());

        String address = "100 Test lane";
        emptyContact.setAddress(address);
            
        Assert.assertEquals(
            "Address setter failed.",
            address,
            emptyContact.getAddress());
            
        Assert.assertEquals(
            "Address setter modified more than just one field.",
            name + " " + email + " " + address,
            emptyContact.toString().trim());

        String number = "555-555-5555";
        emptyContact.setPhoneNumber(number);
            
        Assert.assertEquals(
            "PhoneNumber setter failed.",
            number,
            emptyContact.getPhoneNumber());
            
        Assert.assertEquals(
            "PhoneNumber setter modified more than just one field.",
            name + " " + email + " " + address + " " + number,
            emptyContact.toString().trim());
    }

}

