package pl.edu.agh.ki.frazeusz;

import pl.edu.agh.ki.frazeusz.model.monitor.Monitor;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Choose operation to perform:\n" +
                "[1] Run random demo\n" +
                "[2] Run test on growing data\n" +
                "[3] Run test on constant data");

        final int op = new Scanner(System.in).nextInt();

        final Monitor monitor = new Monitor();

        final JPanel chart = monitor.getPanel();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame("Frazeusz - Monitor");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.add(chart);
                frame.pack();
                frame.setVisible(true);
            }
        });

        Runnable task;
        switch (op) {
            case 2:
                task = new GrowingDataTestTask(monitor);
                break;
            case 3:
                task = new ConstantDataTestTask(monitor);
                break;
            default:
                task = new FakeCrawlerTask(monitor);
                break;
        }

        (new Thread(task)).start();
    }

    private static class FakeCrawlerTask implements Runnable {

        private final Monitor monitor;

        FakeCrawlerTask(Monitor monitor) {
            this.monitor = monitor;
            System.out.println("Starting FakeCrawlerTask...");
        }

        @Override
        public void run() {
            final Random gen = new Random();
            while (true) {
                if (gen.nextDouble() < 0.5) {
                    int readPages = gen.nextInt(1000);
                    int readSize = gen.nextInt(1000) * gen.nextInt(1024);
                    System.out.println("Crawler: processed pages: " + readPages + ", size: " + readSize);
                    monitor.addProcessedPages(readPages, readSize);
                }

                if (gen.nextDouble() < 0.25) {
                    int queueSize = gen.nextInt(1024 * 1024);
                    System.out.println("Crawler: queue size set to: " + queueSize);
                    monitor.setPagesQueueSize(queueSize);
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class GrowingDataTestTask implements Runnable {

        private final Monitor monitor;

        GrowingDataTestTask(Monitor monitor) {
            this.monitor = monitor;
            System.out.println("Starting GrowingDataTestTask...");
        }

        @Override
        public void run() {
            final Random gen = new Random();
            while (true) {
                int readPages = gen.nextInt(1000);
                int readSize = gen.nextInt(1000) * gen.nextInt(1024);
                System.out.println("Crawler: processed pages: " + readPages + ", size: " + readSize);
                monitor.addProcessedPages(readPages, readSize);

                int queueSize = gen.nextInt(1024 * 1024);
                System.out.println("Crawler: queue size set to: " + queueSize);
                monitor.setPagesQueueSize(queueSize);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ConstantDataTestTask implements Runnable {

        private final Monitor monitor;

        ConstantDataTestTask(Monitor monitor) {
            this.monitor = monitor;
            System.out.println("Starting ConstantDataTestTask...");
        }

        @Override
        public void run() {
            final Random gen = new Random();
            int i = 0;
            while (true) {
                int readPages = gen.nextInt(1000);
                int readSize = gen.nextInt(1000) * gen.nextInt(1024);
                System.out.println("Crawler: processed pages: " + readPages + ", size: " + readSize);
                monitor.addProcessedPages(readPages, readSize);

                int queueSize = gen.nextInt(1024 * 1024);
                System.out.println("Crawler: queue size set to: " + queueSize);
                monitor.setPagesQueueSize(queueSize);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
                if (i % 20 == 0) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
