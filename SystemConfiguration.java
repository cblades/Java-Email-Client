package edu.wcu.cs.cs363.team3.project02;

import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Properties;
import java.io.FileInputStream;

/**
 *  Stores persistant data about the system for other components to use, 
 *  specifically the source email address and the SMTP server.
 *
 *  @author Don Coffin
 *  @author Chris Blades
 *  @author Benny Reese
 *
 *  @version 4-2-10
 */
public class SystemConfiguration implements Serializable {

    /** The source email address */
    private String primaryEmail;
    /** The SMTP server to use for sending emails */
    private String SMTPServer;

    /** 
     * Attempts to load the email address and smtp server information from the
     * default properties file. 
     */
    public SystemConfiguration() throws IOException {
        this("default.properties");   
    }

    /**
     * Creates a SystemConfiguration object from a properties file.
     *
     * @param propertiesFilename the location of the properties file to load 
     *                           from
     *
     * @throws IOException if the file is not found or cannot be read from
     *
     */
    public SystemConfiguration(String propertiesFilename) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesFilename));

        this.primaryEmail = props.getProperty("email");
        this.SMTPServer   = props.getProperty("smtp");
    }

    /**
     * Constructor that allows for direct specification of the values of the 
     * fields.
     *
     * @param email the email address to be stored
     * @param SMTPServer the name of the smtp server to use
     */
    public SystemConfiguration(String email, String SMTPServer) {

        this.primaryEmail = email;
        this.SMTPServer = SMTPServer;
    }

    /**
     * Accessor method for the email address
     * 
     * @return the email address.
     */
    public String getEmail() {
        return primaryEmail;
    }

    /**
     * Accessor method for the SMTP server
     *
     * @return the SMTP server currently stored.
     */
    public String getSMTPServer() {
        return SMTPServer;
    }

    /**
     * Mutator method for the email address
     *
     * @param email the email address to set to
     */
    public void setEmail(String email) {
        this.primaryEmail = email;
    }

    /**
     * Mutator method for the SMTP server name
     *
     * @param address the address of the smtp server to use
     */
    public void setSMTPServer(String address) {
        this.SMTPServer = address;
    }
}
