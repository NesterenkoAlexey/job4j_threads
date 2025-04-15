package ru.tasks;

public class ThreadSleep {

    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        for (int index = 0; index < 100; index++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + index + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\rLoaded.");
                }
        );
        thread.start();
    }
}
