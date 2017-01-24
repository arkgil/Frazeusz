package pl.edu.agh.ki.frazeusz;

import pl.edu.agh.ki.frazeusz.model.monitor.Monitor;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        // all work here is done by Plotter
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

        final Runnable fakeCrawlerTask = new Runnable() {
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
        };
        (new Thread(fakeCrawlerTask)).start();
    }
}
