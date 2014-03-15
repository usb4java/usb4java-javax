/*
 * Copyright (C) 2013 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the {@link SimpleUsbConfigurationDescriptor}.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class SimpleUsbConfigurationDescriptorTest
{
    /** The test subject. */
    private static SimpleUsbConfigurationDescriptor descriptor;

    /** Value for {@link SimpleUsbConfigurationDescriptor#bLength()}. */
    private static final byte LENGTH = (byte) 0xff;

    /** Value for {@link SimpleUsbConfigurationDescriptor#bDescriptorType()}. */
    private static final byte DESCRIPTOR_TYPE = (byte) 0xfe;

    /** Value for {@link SimpleUsbConfigurationDescriptor#wTotalLength()}. */
    private static final short TOTAL_LENGTH = (short) 0xffff;

    /** Value for {@link SimpleUsbConfigurationDescriptor#bNumInterfaces()}. */
    private static final byte NUM_INTERFACES = (byte) 0xfc;

    /**
     * Value for {@link SimpleUsbConfigurationDescriptor#bConfigurationValue()}.
     */
    private static final byte CONFIGURATION_VALUE = (byte) 0xfb;

    /** Value for {@link SimpleUsbConfigurationDescriptor#iConfiguration()}. */
    private static final byte CONFIGURATION = (byte) 0xfa;

    /** Value for {@link SimpleUsbConfigurationDescriptor#bmAttributes()}. */
    private static final byte ATTRIBUTES = (byte) 0xf9;

    /** Value for {@link SimpleUsbConfigurationDescriptor#bMaxPower()}. */
    private static final byte MAX_POWER = (byte) 0xf8;

    /** A wrong value for equality test. */
    private static final byte WRONG = 0;

    /**
     * Setup the test subject.
     */
    @BeforeClass
    public static void setUp()
    {
        descriptor = new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, MAX_POWER);
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#bLength()} method.
     */
    @Test
    public void testLength()
    {
        assertEquals(LENGTH, descriptor.bLength());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#bDescriptorType()}
     * method.
     */
    @Test
    public void testDescriptorType()
    {
        assertEquals(DESCRIPTOR_TYPE, descriptor.bDescriptorType());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#wTotalLength()} method.
     */
    @Test
    public void testTotalLength()
    {
        assertEquals(TOTAL_LENGTH, descriptor.wTotalLength());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#bNumInterfaces()}
     * method.
     */
    @Test
    public void testNumInterfaces()
    {
        assertEquals(NUM_INTERFACES, descriptor.bNumInterfaces());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#bConfigurationValue()}
     * method.
     */
    @Test
    public void testConfigurationValue()
    {
        assertEquals(CONFIGURATION_VALUE, descriptor.bConfigurationValue());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#iConfiguration()}
     * method.
     */
    @Test
    public void testConfiguration()
    {
        assertEquals(CONFIGURATION, descriptor.iConfiguration());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#bmAttributes()} method.
     */
    @Test
    public void testAttributes()
    {
        assertEquals(ATTRIBUTES, descriptor.bmAttributes());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#bMaxPower()} method.
     */
    @Test
    public void testMaxPower()
    {
        assertEquals(MAX_POWER, descriptor.bMaxPower());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#hashCode()} method.
     */
    @Test
    public void testHashCode()
    {
        final int code = descriptor.hashCode();
        assertEquals(code, descriptor.hashCode());
        assertEquals(code, new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES,
            MAX_POWER).hashCode());
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#equals(Object)} method.
     */
    @Test
    public void testEquals()
    {
        assertFalse(descriptor.equals(null));
        assertFalse(descriptor.equals(new Object()));
        assertTrue(descriptor.equals(descriptor));
        assertTrue(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            WRONG, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, WRONG, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, WRONG, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, WRONG,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            WRONG, CONFIGURATION, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, WRONG, ATTRIBUTES, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, WRONG, MAX_POWER)));
        assertFalse(descriptor.equals(new SimpleUsbConfigurationDescriptor(
            LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
            CONFIGURATION_VALUE, CONFIGURATION, ATTRIBUTES, WRONG)));
    }

    /**
     * Tests the {@link SimpleUsbConfigurationDescriptor#toString()} method.
     */
    @Test
    public void testToString()
    {
        assertEquals(String.format("Configuration Descriptor:%n"
            + "  bLength                255%n"
            + "  bDescriptorType        254%n"
            + "  wTotalLength         65535%n"
            + "  bNumInterfaces         252%n"
            + "  bConfigurationValue    251%n"
            + "  iConfiguration         250%n"
            + "  bmAttributes          0xf9%n"
            + "    Self Powered%n"
            + "    Remote Wakeup%n"
            + "  bMaxPower              496mA%n"), descriptor.toString());
        assertEquals(String.format("Configuration Descriptor:%n"
            + "  bLength                255%n"
            + "  bDescriptorType        254%n"
            + "  wTotalLength         65535%n"
            + "  bNumInterfaces         252%n"
            + "  bConfigurationValue    251%n"
            + "  iConfiguration         250%n"
            + "  bmAttributes          0x00%n"
            + "    (Bus Powered)%n"
            + "  bMaxPower              496mA%n"),
            new SimpleUsbConfigurationDescriptor(
                LENGTH, DESCRIPTOR_TYPE, TOTAL_LENGTH, NUM_INTERFACES,
                CONFIGURATION_VALUE, CONFIGURATION, WRONG, MAX_POWER)
                .toString());
    }
}
