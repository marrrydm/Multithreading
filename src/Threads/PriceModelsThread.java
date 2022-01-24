package Threads;
import Interface.Transport;

public class PriceModelsThread extends Thread {
    private Transport transport;

    public PriceModelsThread(Transport transport){
        this.transport = transport;
    }
    @Override
    public void run() {
        double[] prices = transport.getArrayPricesOfModels();
        for (double price : prices){
            System.out.println(price);
        }
    }
}
