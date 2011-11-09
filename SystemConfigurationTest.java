package edu.wcu.cs.cs363.team3.project02;

import java.io.File;
import java.io.PrintStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the SystemConfiguration class.
 *
 * @author Chris Blaes
 * @author Don Coffin
 * @author Benny Reese
 * @version 1-4-2010
 */
public class SystemConfigurationTest {
    /**
     * String to use as an email address in tests.
     */
    private String testEmail = "test@test.com";
    
    /**
     * String to use as an smtp server address in tests.
     */
    private String testSmtp  = "smtp.test.com";
    
    /**
     * Implementation of SystemConfiguration to use for tests
     */
    private SystemConfiguration config;

    /**
     * Called by JUnit before each test.  Creates a new SystemConfiguration
     * object.
     */
    @Before
    public void setup() {
        // create new SystemConfiguration object so that tests are entirely
        // independent of each other's changes
        config = new SystemConfiguration(testEmail, testSmtp);
    }

    /**
     * Tests constructor of SystemConfiguration that takes a filename as a
     * parameter and reads email address and SMTP server from file.
     */
    @Test
    public void testPropertiesFileConstructor() {
        File file = new File("test.properties");
        if (! file.canRead()) {
            try {
                PrintStream testFile = new PrintStream(file);
                testFile.println("email = " + testEmail);
                testFile.println("smtp = " + testSmtp);
            } catch (IOException ioe) {
                Assert.fail("Failed to create test.properties.");
            }
        }
        try {
            this.config = new SystemConfiguration("test.properties");
        } catch (IOException ioe) {
            Assert.fail("SystemConfiguration(propertiesFile) failed.");
        }


        Assert.assertEquals(
            "Properties file Constructor, email should be " + testEmail, 
            testEmail,
            config.getEmail());

        Assert.assertEquals(
            "Properties file Constructor, SMTP server should be " + testSmtp,
            testSmtp, 
            config.getSMTPServer());
    }

    /**
     * Tests SystemConfiguration constructor that takes as parameters an 
     * email address and the address of an smtp server.
     */
    @Test
    public void BaseConstructorTest() {
        Assert.assertEquals(
            "Base Constructor, email should be " + testEmail, 
            testEmail,
            config.getEmail());

        Assert.assertEquals(
            "Base Constructor, SMTP server should be " + testSmtp,
            testSmtp, 
            config.getSMTPServer());
    }


    /**
     * Tests the setEmail method of the SystemConfiguration class.
     */
    @Test
    public void setEmailTest() {
        config.setEmail("foo@test.com");

        Assert.assertEquals(
            "SystemConfiguration.getEmail should be foo@test.com",
            "foo@test.com",
            config.getEmail());
    }

    /**
     * Tests the setSMTPServer method of the SystemConfiguration class.
     */
    @Test
    public void setSMTPServerTest() {
        config.setSMTPServer("smtp.foo.com");

        Assert.assertEquals(
            "SystemConfiguration.getEmail should be smtp.foo.com",
            "smtp.foo.com",
            config.getSMTPServer());
    }
}
