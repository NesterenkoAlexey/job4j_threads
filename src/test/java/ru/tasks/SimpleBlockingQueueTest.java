package test.java.ru.tasks;

import main.java.ru.tasks.thread_safe.SimpleBlockingQueue;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        List<Integer> expectedList = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10000);
        AtomicInteger countOffer = new AtomicInteger();

        Runnable consumer1 = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    expectedList.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable producer1 = () -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    queue.offer(i);
                    countOffer.getAndIncrement();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread consumer2 = new Thread(consumer1, "consumer2");
        Thread producer2 = new Thread(producer1, "producer2");

        consumer2.start();
        producer2.start();
        producer2.join();

        while (countOffer.get() != expectedList.size()) {
            System.out.println("Ожидаем, что вытащили все числа из очереди ");
        }

        consumer2.interrupt();
        consumer2.join();

        Assertions.assertEquals(countOffer.get(), expectedList.size());
    }

}
