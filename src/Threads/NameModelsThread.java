package Threads;
import Interface.Transport;

public class NameModelsThread extends Thread {
    private Transport transport;

    public NameModelsThread(Transport transport) {
        this.transport = transport;
    }
    @Override
    public void run() {
        String[] names = transport.getArrayNamesOfModels();
        for (String name : names){
            System.out.println(name);
        }
    }
}
