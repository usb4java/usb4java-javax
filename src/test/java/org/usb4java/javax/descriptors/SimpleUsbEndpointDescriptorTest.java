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
 * Tests the {@link SimpleUsbEndpointDescriptor}.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class SimpleUsbEndpointDescriptorTest
{
    /** The test subject. */
    private static SimpleUsbEndpointDescriptor descriptor;

    /** Value for {@link SimpleUsbEndpointDescriptor#bLength()}. */
    private static final byte LENGTH = (byte) 0xff;

    /** Value for {@link SimpleUsbEndpointDescriptor#bDescriptorType()}. */
    private static final byte DESCRIPTOR_TYPE = (byte) 0xfe;

    /** Value for {@link SimpleUsbEndpointDescriptor#bEndpointAddress()}. */
    private static final byte ENDPOINT_ADDRESS = (byte) 0xfd;

    /** Value for {@link SimpleUsbEndpointDescriptor#bmAttributes()}. */
    private static final byte ATTRIBUTES = (byte) 0xfc;

    /** Value for {@link SimpleUsbEndpointDescriptor#wMaxPacketSize()}. */
    private static final short MAX_PACKET_SIZE = (short) 0xffff;

    /** Value for {@link SimpleUsbEndpointDescriptor#bInterval()}. */
    private static final byte INTERVAL = (byte) 0xfb;

    /** A wrong value for equality test. */
    private static final byte WRONG = 0;

    /**
     * Setup the test subject.
     */
    @BeforeClass
    public static void setUp()
    {
        descriptor = new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, ATTRIBUTES,
            MAX_PACKET_SIZE, INTERVAL);
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#bLength()} method.
     */
    @Test
    public void testLength()
    {
        assertEquals(LENGTH, descriptor.bLength());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#bDescriptorType()} method.
     */
    @Test
    public void testDescriptorType()
    {
        assertEquals(DESCRIPTOR_TYPE, descriptor.bDescriptorType());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#bEndpointAddress()} method.
     */
    @Test
    public void testEndpointAddress()
    {
        assertEquals(ENDPOINT_ADDRESS, descriptor.bEndpointAddress());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#bmAttributes()} method.
     */
    @Test
    public void testAttributes()
    {
        assertEquals(ATTRIBUTES, descriptor.bmAttributes());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#wMaxPacketSize()} method.
     */
    @Test
    public void testMaxPacketSize()
    {
        assertEquals(MAX_PACKET_SIZE, descriptor.wMaxPacketSize());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#bInterval()} method.
     */
    @Test
    public void testInterval()
    {
        assertEquals(INTERVAL, descriptor.bInterval());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#hashCode()} method.
     */
    @Test
    public void testHashCode()
    {
        final int code = descriptor.hashCode();
        assertEquals(code, descriptor.hashCode());
        assertEquals(code, new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, ATTRIBUTES,
            MAX_PACKET_SIZE, INTERVAL).hashCode());
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#equals(Object)} method.
     */
    @Test
    public void testEquals()
    {
        assertFalse(descriptor.equals(null));
        assertFalse(descriptor.equals(new Object()));
        assertTrue(descriptor.equals(descriptor));
        assertTrue(descriptor.equals(new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, ATTRIBUTES,
            MAX_PACKET_SIZE, INTERVAL)));
        assertFalse(descriptor.equals(new SimpleUsbEndpointDescriptor(
            WRONG, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, ATTRIBUTES,
            MAX_PACKET_SIZE, INTERVAL)));
        assertFalse(descriptor.equals(new SimpleUsbEndpointDescriptor(
            LENGTH, WRONG, ENDPOINT_ADDRESS, ATTRIBUTES,
            MAX_PACKET_SIZE, INTERVAL)));
        assertFalse(descriptor.equals(new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, WRONG, ATTRIBUTES,
            MAX_PACKET_SIZE, INTERVAL)));
        assertFalse(descriptor.equals(new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, WRONG,
            MAX_PACKET_SIZE, INTERVAL)));
        assertFalse(descriptor.equals(new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, ATTRIBUTES,
            WRONG, INTERVAL)));
        assertFalse(descriptor.equals(new SimpleUsbEndpointDescriptor(
            LENGTH, DESCRIPTOR_TYPE, ENDPOINT_ADDRESS, ATTRIBUTES,
            MAX_PACKET_SIZE, WRONG)));
    }

    /**
     * Tests the {@link SimpleUsbEndpointDescriptor#toString()} method.
     */
    @Test
    public void testToString()
    {
        assertEquals(String.format("Endpoint Descriptor:%n"
            + "  bLength                255%n"
            + "  bDescriptorType        254%n"
            + "  bEndpointAddress      0xfd  EP 13 IN%n"
            + "  bmAttributes           252%n"
            + "    Transfer Type             Control%n"
            + "    Synch Type                Synchronous%n"
            + "    Usage Type                Reserved%n"
            + "  wMaxPacketSize       65535%n"
            + "  bInterval              251%n"), descriptor.toString());
    }
}
