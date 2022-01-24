package Threads;
import Interface.Transport;

public class TransportSynchronizer {
    private Transport v;
    private volatile int current = 0;
    private Object lock = new Object();
    private boolean set = false;

    public TransportSynchronizer(Transport v) {
        this.v = v;
    }

    public double printPrice() throws InterruptedException {
        double val;
        synchronized (lock) {
            double[] p = v.getArrayPricesOfModels();
            if (!canPrintPrice()) throw new InterruptedException();
            while (!set) {
                lock.wait();
            }
            val = p[current++];
            System.out.println("Print price: " + val);
            set = false;
            lock.notifyAll();
        }
        return val;
    }

    public void printModel() throws InterruptedException {
        synchronized (lock) {
            String[] s = v.getArrayNamesOfModels();
            if (!canPrintModel()) throw new InterruptedException();
            while (set) {
                lock.wait();
            }
            System.out.println("Print model: " + s[current]);
            set = true;
            lock.notifyAll();
        }
    }

    public boolean canPrintPrice() {
        return current < v.getLenModels();
    }

    public boolean canPrintModel() {
        return (!set && current < v.getLenModels()) || (set && current < v.getLenModels() - 1);
    }
}
