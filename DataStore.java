package edu.wcu.cs.cs363.team3.project02;

import java.util.Collection;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.ClassCastException;

/**
 * Singleton class responsible for providing public access to a 
 * SystemConfiguration object and a list of contacts.
 *
 * @author Benny Reese
 * @author Chris Blades
 * @author Don Coffin
 *
 * @version 4-6-10
 */
public class DataStore {
    /** the single instance of this class.  */
    private static DataStore instance = new DataStore();

    /** a public accessor method to get the single instance. */
    public static DataStore get() {
        return instance;
    }

    /** 
     * a constant specifying the file name of the storage location of a system
     * config object.
     */
    private static final String sysConfigLocation = "systemconfig.dat";

    /**
     * a constant specifying the file name of the storage location of the 
     * collection of contacts.
     */
    private static final String emailContactsLocation = "contacts.dat";

    /** 
     *  Constructor - private to disable instantiation of this class 
     *  externally.
     */
    private DataStore() {
        // do nothing. Here to enforce the Singleton pattern.
    }

    /** the default SystemConfiguration object. */
    private SystemConfiguration config;

    /** list of EmailContacts*/
    private Collection<EmailContact> contacts;

    /** 
     * Returns the default SystemConfiguration object. Lazily initializes it.
     * Attempts to load the SystemConfiguration from a file, failing that 
     * attempts the default constructor, failing that, provides fields.
     * @return the default SystemConfiguration object.
     */
    public SystemConfiguration getSystemConfiguration() {
        if (config == null) {
            try {
                FileInputStream fis = new FileInputStream(sysConfigLocation);
                ObjectInputStream ois = new ObjectInputStream(fis);
                config = (SystemConfiguration) ois.readObject();
                return config;
            } catch (FileNotFoundException fnfe) {
                System.out.println("System config file not found; creating " +
                                    " default system configuration.");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("System config file malformed; creating " +
                                    "default system configuration.");
            } catch (ClassCastException cce) {
                System.out.println("Wrong object in system config file; " +
                                    "creating default system configuration.");
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage() + "; creating default " +
                                    "system configuration.");
            }
            try {
                config = new SystemConfiguration();
            } catch (IOException ioe) {
                config = new SystemConfiguration("blank@localhost.net", 
                                                 "localhost");
            }
        }
        return config;
    }

    /**
     * Returns all EmailContacts that have been saved on the system. Lazily 
     * loads them from disk. If there is no contacts file, initializes with
     * an empty collection.
     *
     * @return a collection of EmailContacts that have been stored on disk, or 
     * an empty collection.
     */
    public Collection<EmailContact> getContacts() {
        if (contacts == null) {
            try {
                FileInputStream fis = new FileInputStream(
                                                emailContactsLocation);
                ObjectInputStream ois = new ObjectInputStream(fis);
                @SuppressWarnings("unchecked")
                Collection<EmailContact> tempCol = 
                                (Collection<EmailContact>) ois.readObject();
                contacts = tempCol;
                return contacts;
            } catch (FileNotFoundException fnfe) {
                System.out.println("contacts file not found; creating " +
                                    " empty contact list.");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("contacts file malformed; creating " +
                                    "empty contact list.");
            } catch (ClassCastException cce) {
                System.out.println("Wrong object in contacts file; " +
                                    "creating empty contact list.");
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage() + "; creating empty " +
                                    "contact list.");
            }
            contacts = new LinkedList<EmailContact>();
        }
        return contacts;
    }

    /**
     *  Called to save the state of the DataStore - saves the configuration 
     *  file 
     *
     */
    public void saveState() throws IOException {
        FileOutputStream fos;
        ObjectOutputStream oos;
        if (config != null) {
            fos = new FileOutputStream(sysConfigLocation);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(config);
        }
        if (contacts != null) {
            fos = new FileOutputStream(emailContactsLocation);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(contacts);
        }
    }
}
