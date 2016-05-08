/*
 * Copyright (C) 2011 Klaus Reimer <k@ailis.de>
 * See LICENSE.md for licensing information.
 */

package org.usb4java.javax;

import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /** Key name for USB IRP executor. */
    private static final String EXECUTOR_SERVICE_KEY = KEY_BASE + "irpExecutorService";

    /** The timeout for USB communication in milliseconds. */
    private int timeout = DEFAULT_TIMEOUT;
    
    /** The scan interval in milliseconds. */
    private int scanInterval = DEFAULT_SCAN_INTERVAL;

    /** The executor service factory. */
    private ExecutorServiceProvider executorService = new ExecutorServiceProvider() {
        private final AtomicInteger poolNumber = new AtomicInteger(1);

        class LocalThreadFactory extends Object implements ThreadFactory {
            private final ThreadGroup group;
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final String namePrefix;

            LocalThreadFactory() {
                SecurityManager s = System.getSecurityManager();
                group = (s != null) ? s.getThreadGroup() :
                        Thread.currentThread().getThreadGroup();
                namePrefix = "usb4java-irp-" +
                        poolNumber.getAndIncrement() +
                        "-thread-";
            }

            public Thread newThread(Runnable r) {
                Thread t = new Thread(group, r,
                        namePrefix + threadNumber.getAndIncrement(),
                        0);
                t.setDaemon(true);
                if (t.getPriority() != Thread.MAX_PRIORITY)
                    t.setPriority(Thread.MAX_PRIORITY);
                return t;
            }
        }

        public ExecutorService newExecutorService() {
            /* The default executor is a pool of max 1 thread, with 3s timeout. */
            ThreadPoolExecutor es = new ThreadPoolExecutor(0, 1,
                    3L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>());
            es.setThreadFactory(new LocalThreadFactory());
            return es;
        }
    };

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

        // Read the irp executor class
        if (properties.containsKey(EXECUTOR_SERVICE_KEY))
        {
            try {
                Class<?> cls = getClass().getClassLoader().loadClass(properties.getProperty(
                        EXECUTOR_SERVICE_KEY));
                this.executorService = (ExecutorServiceProvider)cls.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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
     * Creates a new non-parallel execution service. Defaults to single thread exec
     * @return new non-parallel ExecutorService
     */
    public ExecutorService newExecutorService() { return this.executorService.newExecutorService(); }
}
