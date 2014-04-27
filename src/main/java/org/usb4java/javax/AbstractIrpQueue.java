/*
 * Copyright (C) 2011 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.usb.UsbControlIrp;
import javax.usb.UsbException;
import javax.usb.UsbIrp;
import javax.usb.UsbShortPacketException;

import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;


/**
 * Abstract base class for IRP queues.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The type of IRPs this queue holds.
 */
abstract class AbstractIrpQueue<T extends UsbIrp>
{
    /** If queue is currently aborting. */
    private volatile boolean aborting;

    /** The USB device. */
    private final AbstractDevice device;

    /** The non-parallel ExecutorService we will use for this queue on this device. */
    private final ExecutorService singleThreadExecutor;

    /** The job counter for active jobs in this queue. */
    private final AtomicInteger activeJobs = new AtomicInteger(0);

    /**
     * Constructor.
     * 
     * @param device
     *            The USB device. Must not be null.
     */
    AbstractIrpQueue(final AbstractDevice device)
    {
        if (device == null)
            throw new IllegalArgumentException("device must be set");
        this.device = device;

        this.singleThreadExecutor = Services.getInstance().getConfig().newExecutorService();
    }

    /**
     * Queues the specified control IRP for processing.
     * 
     * @param irp
     *            The control IRP to queue.
     */
    public final void add(final T irp)
    {
        singleThreadExecutor.execute(new Runnable() {
            final T irp0 = irp;

            @Override
            public void run() {
                activeJobs.incrementAndGet();

                try {
                    if (!aborting) {
                        try {
                            processIrp(irp0);
                        } catch (final UsbException e) {
                            irp0.setUsbException(e);
                        }

                        irp0.complete();
                        finishIrp(irp0);
                    }
                } finally {
                    activeJobs.decrementAndGet();
                }
            }
        });
    }

    /**
     * Processes the IRP.
     * 
     * @param irp
     *            The IRP to process.
     * @throws UsbException
     *             When processing the IRP fails.
     */
    protected abstract void processIrp(final T irp) throws UsbException;

    /**
     * Called after IRP has finished. This can be implemented to send events for
     * example.
     * 
     * @param irp
     *            The IRP which has been finished.
     */
    protected abstract void finishIrp(final UsbIrp irp);

    /**
     * Aborts all queued IRPs. The IRP which is currently processed can't be
     * aborted. This method returns as soon as no more IRPs are in the queue and
     * no more are processed.
     */
    public final void abort()
    {
        this.aborting = true;

        singleThreadExecutor.shutdown();
        try {
            singleThreadExecutor.awaitTermination(4, TimeUnit.SECONDS);
            this.aborting = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Checks if queue is busy. A busy queue is a queue which is currently
     * processing IRPs or which still has IRPs in the queue.
     * 
     * @return True if queue is busy, false if not.
     */
    public final synchronized boolean isBusy()
    {
        return activeJobs.get() > 0;
    }

    /**
     * Returns the configuration.
     * 
     * @return The configuration.
     */
    protected final Config getConfig()
    {
        return Services.getInstance().getConfig();
    }

    /**
     * Returns the USB device.
     * 
     * @return The USB device. Never null.
     */
    protected final AbstractDevice getDevice()
    {
        return this.device;
    }
    
    /**
     * Processes the control IRP.
     * 
     * @param irp
     *            The IRP to process.
     * @throws UsbException
     *             When processing the IRP fails.
     */
    protected final void processControlIrp(final UsbControlIrp irp)
        throws UsbException
    {
        final ByteBuffer buffer =
            ByteBuffer.allocateDirect(irp.getLength());
        buffer.put(irp.getData(), irp.getOffset(), irp.getLength());
        buffer.rewind();
        final DeviceHandle handle = getDevice().open();
        final int result = LibUsb.controlTransfer(handle, irp.bmRequestType(),
            irp.bRequest(), irp.wValue(), irp.wIndex(), buffer,
            getConfig().getTimeout());
        if (result < 0)
        {
            throw ExceptionUtils.createPlatformException(
                "Unable to submit control message", result);
        }
        buffer.rewind();
        buffer.get(irp.getData(), irp.getOffset(), result);
        irp.setActualLength(result);
        if (irp.getActualLength() != irp.getLength()
            && !irp.getAcceptShortPacket())
        {
            throw new UsbShortPacketException();
        }
    }
    
    /**
     * Checks if this queue is currently aborting.
     * 
     * @return True if queue is aborting, false if not.
     */
    protected final boolean isAborting()
    {
        return this.aborting;
    }
}
