package Threads;

public class NameModelsRunnable implements Runnable{
    private TransportSynchronizer synchronizer;

    public NameModelsRunnable(TransportSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public void run() {
        while (synchronizer.canPrintModel()) {
            try {
                synchronizer.printModel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
