/*
 * Copyright (C) 2013 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;
import org.usb4java.javax.Config;

/**
 * Tests the {@link Config} class.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class ConfigTest
{
    /**
     * Tests the default configuration
     */
    @Test
    public void testDefaultConfiguration()
    {
        final Properties properties = new Properties();
        final Config config = new Config(properties);
        assertEquals(5000, config.getTimeout());
        assertEquals(500, config.getScanInterval());
        assertFalse(config.isUseUSBDK());
    }

    /**
     * Tests the scan interval configuration.
     */
    @Test
    public void testScanIntervalConfiguration()
    {
        final Properties properties = new Properties();
        properties.put("org.usb4java.javax.scanInterval", "123");
        final Config config = new Config(properties);
        assertEquals(123, config.getScanInterval());
    }

    /**
     * Tests the timeout configuration.
     */
    @Test
    public void testTimeoutConfiguration()
    {
        final Properties properties = new Properties();
        properties.put("org.usb4java.javax.timeout", "1234");
        final Config config = new Config(properties);
        assertEquals(1234, config.getTimeout());
    }

    /**
     * Tests the USBDK usage flag configuration.
     */
    @Test
    public void testUseUSBDKConfiguration()
    {
        final Properties properties = new Properties();
        properties.put("org.usb4java.javax.useUSBDK", "true");
        final Config config = new Config(properties);
        assertTrue(config.isUseUSBDK());
    }
}
