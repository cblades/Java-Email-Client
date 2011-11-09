package edu.wcu.cs.cs363.team3.project02;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testsuite for the DataStore.
 *
 * @author Benny Reese
 * @author Chris Blades
 * @author Don Coffin
 *
 * @version 4-6-10
 */
public class DataStoreTest {

    /**
     *  Tests to make sure the DataStore will return the same instance of a
     *  SystemConfiguration object two calls in a row. There is not much else 
     *  a JUnit test can do.
     */
    @Test
    public void testConfigGetter() {
        Assert.assertTrue(DataStore.get().getSystemConfiguration() == 
                          DataStore.get().getSystemConfiguration());

    }
    
    /**
     *  Tests to make sure the DataStore will return the same collection of
     *  contacts two calls in a row.
     */
    @Test
    public void testContactsGetter() {
        Assert.assertTrue(DataStore.get().getContacts() == 
                          DataStore.get().getContacts());
    }

}
