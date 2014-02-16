/*
 * Copyright (C) 2013 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */
package org.usb4java.javax.adapter;

import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;

import org.junit.Test;
import org.usb4java.javax.adapter.UsbPipeAdapter;

/**
 * Test the {@link UsbPipeAdapter} class. There is not really anything to
 * test there. This class just ensures that the class exists and provides
 * the needed methods.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class UsbPipeAdapterTest
{
    /**
     * Ensure the existence of the needed methods.
     */
    @Test
    public void testAbstractMethods()
    {
        final UsbPipeListener adapter = new UsbPipeAdapter()
        {
            // Empty
        };
        adapter.dataEventOccurred((UsbPipeDataEvent) null);
        adapter.errorEventOccurred((UsbPipeErrorEvent) null);
    }
}
