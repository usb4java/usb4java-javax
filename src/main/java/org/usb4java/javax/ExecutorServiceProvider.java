package org.usb4java.javax;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceProvider {
    public ExecutorService newExecutorService();
}