package Threads;
import Interface.Transport;
import java.util.concurrent.locks.ReentrantLock;

public class NameReentrantLock implements Runnable{
    private Transport vehicle;
    private ReentrantLock lock;

    public NameReentrantLock(Transport vehicle, ReentrantLock lock) {
        this.vehicle = vehicle;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            String[] names = vehicle.getArrayNamesOfModels();
            for (String name : names) {
                System.out.println(name);
            }
        } finally {
            lock.unlock();
        }
    }
}
