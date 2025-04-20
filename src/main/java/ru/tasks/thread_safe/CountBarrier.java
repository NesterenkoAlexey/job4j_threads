package main.java.ru.tasks.thread_safe;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;
    @GuardedBy("monitor")
    private final int total;
    @GuardedBy("monitor")
    private int count;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total && !Thread.currentThread().isInterrupted()) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (!Thread.currentThread().isInterrupted()) {
                System.out.println("Готов к работе - " + Thread.currentThread().getName());
            }
        }
    }
}
