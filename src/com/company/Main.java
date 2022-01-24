package com.company;
import Auto.*;
import Exceptions.*;
import Interface.*;
import Threads.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        try {
            //Task1();
            //Task2();
            //Task3();
            //Task4();
            try {
                Task5();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
    }

    public static void Task1() throws DuplicateModelNameException {
        Transport transport = new Car("BMW", 1000);
        Thread name = new NameModelsThread(transport);
        Thread price = new PriceModelsThread(transport);
        name.setPriority(Thread.MAX_PRIORITY);
        price.setPriority(Thread.MIN_PRIORITY);
        name.start();
        price.start();
    }

    public static void Task2() throws DuplicateModelNameException {
        Transport vehicle = new Car("BMW", 100);
        TransportSynchronizer synchronizer = new TransportSynchronizer(vehicle);
        Thread priceThread = new Thread(new PriceModelsRunnable(synchronizer));
        Thread nameThread = new Thread(new NameModelsRunnable(synchronizer));
        priceThread.start();
        nameThread.start();
    }

    public static void Task3() throws DuplicateModelNameException {
        Transport moped = new Moped("BMW", 50);
        ReentrantLock lock = new ReentrantLock();
        Thread nameThread = new Thread(new NameReentrantLock(moped, lock));
        Thread priceThread = new Thread(new PriceReentrantLock(moped, lock));
        priceThread.start();
        nameThread.start();

    }

    public static void Task4() throws DuplicateModelNameException {
        Car car = new Car("BMW", 5);
        Moped moped = new Moped("Toyota", 5);
        Motorbike motorbike = new Motorbike("Harley", 5);
        Scooter scooter = new Scooter("Stells", 5);
        Transport[] transports = {car, moped, motorbike, scooter};
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (Transport transport : transports) {
            pool.submit(new BrandRunnable(transport));
        }
        pool.shutdown();
    }

    public static void Task5() throws InterruptedException,DuplicateModelNameException {
        String[] files = {"File1.txt", "File2.txt", "File3.txt", "File4.txt", "File5.txt"};
        ArrayBlockingQueue<Transport> queue = new ArrayBlockingQueue<>(1);
        for (String file : files) {
            FileRunnable fileRunnable = new FileRunnable(file, queue);
            Thread thread = new Thread(fileRunnable);
            thread.start();
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println(queue.take().getMotoBrand());
        }
    }
}