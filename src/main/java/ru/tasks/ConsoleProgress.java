package ru.tasks;

public class ConsoleProgress implements Runnable {

    private final char[] process = new char[]{'-', '\\', '|', '/'};

    @Override
    public void run() {
        int i = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r Loading ... " + process[i % process.length]);
                Thread.currentThread().sleep(500L);
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
