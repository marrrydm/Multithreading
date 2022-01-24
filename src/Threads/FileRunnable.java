package Threads;
import Auto.Car;
import Interface.Transport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class   FileRunnable implements Runnable{
    private Transport transport;
    private String file;
    private ArrayBlockingQueue<Transport> queue;

    public FileRunnable(String file, ArrayBlockingQueue<Transport> queue) {
        this.file = file;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String brand = in.readLine();
            transport = new Car(brand);
            queue.add(transport);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
