package com.erian.microgrid.webapp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class DeviceTest {

	
    public DeviceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    private Device device;

    @Before
    public void setUp() {
        device = new Device();
        device.setName("test name");
        device.setDescription("test Description");
    }

    @After
    public void tearDown() {
        device = null;
    }

    /**
     * Test of getId method, of class Device.
     */
    @Test
    public void testPojo() {
        assertEquals("test name", device.getName());
        assertEquals("test Description", device.getDescription());
    }

}
