/*
 * Copyright (C) 2014 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.usb.UsbConfiguration;
import javax.usb.UsbConst;
import javax.usb.UsbEndpoint;
import javax.usb.UsbException;
import javax.usb.UsbInterface;
import javax.usb.UsbInterfaceDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests the {@link RootHubInterface} class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class RootHubInterfaceTest
{
    /** The USB configuration. */
    private UsbConfiguration config;

    /** The test subject. */
    private RootHubInterface iface;

    /**
     * Initialize the test.
     */
    @Before
    public void init()
    {
        this.config = Mockito.mock(UsbConfiguration.class);
        this.iface = new RootHubInterface(this.config);
    }

    /**
     * Tests the {@link RootHubInterface#claim()} method.
     * 
     * @throws UsbException
     *             Excepted exception.
     */
    @Test(expected = UsbException.class)
    public void testClaim() throws UsbException
    {
        this.iface.claim();
    }

    /**
     * Tests the {@link RootHubInterface#claim(javax.usb.UsbInterfacePolicy)}
     * method.
     * 
     * @throws UsbException
     *             Excepted exception.
     */
    @Test(expected = UsbException.class)
    public void testClaimWithPolicy() throws UsbException
    {
        this.iface.claim(null);
    }

    /**
     * Tests the {@link RootHubInterface#release()} method.
     * 
     * @throws UsbException
     *             Excepted exception.
     */
    @Test(expected = UsbException.class)
    public void testRelese() throws UsbException
    {
        this.iface.release();
    }

    /**
     * Tests the {@link RootHubInterface#isClaimed()} method.
     */
    @Test
    public void testIsClaimed()
    {
        assertTrue(this.iface.isClaimed());
    }

    /**
     * Tests the {@link RootHubInterface#isActive()} method.
     */
    @Test
    public void testIsActive()
    {
        assertTrue(this.iface.isActive());
    }

    /**
     * Tests the {@link RootHubInterface#getNumSettings()} method.
     */
    @Test
    public void testGetNumSettings()
    {
        assertEquals(0, this.iface.getNumSettings());
    }

    /**
     * Tests the {@link RootHubInterface#getActiveSettingNumber()} method.
     */
    @Test
    public void testGetActiveSettingNumber()
    {
        assertEquals(0, this.iface.getActiveSettingNumber());
    }

    /**
     * Tests the {@link RootHubInterface#getActiveSetting()} method.
     */
    @Test
    public void testGetActiveSetting()
    {
        assertSame(this.iface, this.iface.getActiveSetting());
    }

    /**
     * Tests the {@link RootHubInterface#getSetting(byte)} method.
     */
    @Test
    public void testGetSetting()
    {
        assertSame(this.iface, this.iface.getSetting((byte) 0));
    }
    
    /**
     * Tests the {@link RootHubInterface#containsSetting(byte)} method.
     */
    @Test
    public void testContainsSetting()
    {
        assertFalse(this.iface.containsSetting((byte) 0));
    }

    /**
     * Tests the {@link RootHubInterface#getSettings()} method.
     */
    @Test
    public void testGetSettings()
    {
        final List<UsbInterface> settings = this.iface.getSettings();
        assertEquals(0, settings.size());
    }

    /**
     * Tests the {@link RootHubInterface#getUsbEndpoints()} method.
     */
    @Test
    public void testGetUsbEndpoints()
    {
        final List<UsbEndpoint> endpoints = this.iface.getUsbEndpoints();
        assertEquals(0, endpoints.size());
    }

    /**
     * Tests the {@link RootHubInterface#getUsbEndpoint(byte)} method.
     */
    @Test
    public void testGetUsbEndpoint()
    {
        assertNull(this.iface.getUsbEndpoint((byte) 0));
    }

    /**
     * Tests the {@link RootHubInterface#containsUsbEndpoint(byte)} method.
     */
    @Test
    public void testContainsUsbEndpoint()
    {
        assertFalse(this.iface.containsUsbEndpoint((byte) 0));
    }

    /**
     * Tests the {@link RootHubInterface#getUsbConfiguration()} method.
     */
    @Test
    public void testGetUsbConfiguration()
    {
        assertSame(this.config, this.iface.getUsbConfiguration());
    }

    /**
     * Tests the {@link RootHubInterface#getUsbInterfaceDescriptor()} method.
     */
    @Test
    public void testGetUsbInterfaceDescriptor()
    {
        final UsbInterfaceDescriptor desc =
            this.iface.getUsbInterfaceDescriptor();
        assertEquals(UsbConst.DESCRIPTOR_MIN_LENGTH_INTERFACE, desc.bLength());
        assertEquals(UsbConst.DESCRIPTOR_TYPE_INTERFACE,
            desc.bDescriptorType());
        assertEquals(0, desc.bInterfaceNumber());
        assertEquals(0, desc.bAlternateSetting());
        assertEquals(0, desc.bNumEndpoints());
        assertEquals(UsbConst.HUB_CLASSCODE, desc.bInterfaceClass());
        assertEquals(0, desc.bInterfaceSubClass());
        assertEquals(0, desc.bInterfaceProtocol());
        assertEquals(0, desc.iInterface());
    }

    /**
     * Tests the {@link RootHubInterface#getInterfaceString()} method.
     */
    @Test
    public void testGetInterfaceString()
    {
        assertNull(this.iface.getInterfaceString());
    }
}
