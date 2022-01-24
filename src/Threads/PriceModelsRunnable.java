package Threads;

public class PriceModelsRunnable implements Runnable{
    private TransportSynchronizer synchronizer;

    public PriceModelsRunnable(TransportSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public void run() {
        while (synchronizer.canPrintPrice()){
            try {
                synchronizer.printPrice();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
