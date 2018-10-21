/*
 * Copyright (C) 2011 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax;

import java.util.Properties;

/**
 * Configuration.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
final class Config
{
    /** Base key name for properties. */
    private static final String KEY_BASE = "org.usb4java.javax.";

    /** The default USB communication timeout in milliseconds. */
    private static final int DEFAULT_TIMEOUT = 5000;

    /** The default scan interval in milliseconds. */
    private static final int DEFAULT_SCAN_INTERVAL = 500;

    /** Key name for USB communication timeout. */
    private static final String TIMEOUT_KEY = KEY_BASE + "timeout";

    /** Key name for USB communication timeout. */
    private static final String SCAN_INTERVAL_KEY = KEY_BASE + "scanInterval";

    /** Key name for the USBDK usage flag. */
    private static final String USE_USBDK_KEY = KEY_BASE + "useUSBDK";

    /** The timeout for USB communication in milliseconds. */
    private int timeout = DEFAULT_TIMEOUT;

    /** The scan interval in milliseconds. */
    private int scanInterval = DEFAULT_SCAN_INTERVAL;

    /** If USBDK is to be used on Windows. */
    private boolean useUSBDK = false;

    /**
     * Constructs new configuration from the specified properties.
     *
     * @param properties
     *            The properties to read the configuration from.
     */
    Config(final Properties properties)
    {
        // Read the USB communication timeout
        if (properties.containsKey(TIMEOUT_KEY))
        {
            this.timeout = Integer.valueOf(properties.getProperty(TIMEOUT_KEY));
        }

        // Read the USB device scan interval
        if (properties.containsKey(SCAN_INTERVAL_KEY))
        {
            this.scanInterval = Integer.valueOf(properties.getProperty(
                SCAN_INTERVAL_KEY));
        }

        // Read the USBDK usage flag
        if (properties.containsKey(USE_USBDK_KEY))
        {
            this.useUSBDK = Boolean.valueOf(properties.getProperty(USE_USBDK_KEY));
        }
    }

    /**
     * Returns the USB communication timeout in milliseconds.
     *
     * @return The USB communication timeout in milliseconds.
     */
    public int getTimeout()
    {
        return this.timeout;
    }

    /**
     * Returns the scan interval in milliseconds.
     *
     * @return The scan interval in milliseconds.
     */
    public int getScanInterval()
    {
        return this.scanInterval;
    }

    /**
     * Check if USBDK is to be used on Windows.
     *
     * @return True if USBDK is to be used, false if not.
     */
    public boolean isUseUSBDK()
    {
        return this.useUSBDK;
    }
}
