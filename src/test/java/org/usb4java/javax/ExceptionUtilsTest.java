/*
 * Copyright (C) 2014 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;

import javax.usb.UsbPlatformException;

import org.junit.Test;
import org.usb4java.LibUsb;

/**
 * Tests the {@link ExceptionUtils} class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class ExceptionUtilsTest
{
    /**
     * Tests the {@link ExceptionUtils#createPlatformException(String, int)}
     * method.
     */
    @Test
    public void testCreatePlatformException()
    {
        final UsbPlatformException e =
            ExceptionUtils.createPlatformException("Custom message",
                LibUsb.ERROR_IO);
        assertEquals("USB error 1: Custom message: Input/Output Error",
            e.getMessage());
        assertEquals(LibUsb.ERROR_IO, e.getErrorCode());
    }

    /**
     * Ensure constructor is private.
     * 
     * @throws Exception
     *             When constructor test fails.
     */
    @Test
    public void testPrivateConstructor() throws Exception
    {
        assertEquals(0, ExceptionUtils.class.getConstructors().length);
        final Constructor<?> c = ExceptionUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }
}
