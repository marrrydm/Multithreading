package Threads;
import Interface.Transport;

public class BrandRunnable implements Runnable {
    private Transport vehicle;

    public BrandRunnable(Transport vehicle) {
        this.vehicle = vehicle;
    }
    @Override
    public void run() {
        System.out.println(vehicle.getMotoBrand());
    }
}
