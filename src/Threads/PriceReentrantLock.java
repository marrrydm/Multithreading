package Threads;
import Interface.Transport;
import java.util.concurrent.locks.ReentrantLock;

public class PriceReentrantLock implements Runnable{
    private Transport vehicle;
    private ReentrantLock lock;

    public PriceReentrantLock(Transport vehicle, ReentrantLock lock) {
        this.vehicle = vehicle;
        this.lock = lock;
    }
    @Override
    public void run() {
        lock.lock();
        try {
            double[] prices = vehicle.getArrayPricesOfModels();
            for (double price : prices) {
                System.out.println(price);
            }
        } finally {
            lock.unlock();
        }
    }
}
