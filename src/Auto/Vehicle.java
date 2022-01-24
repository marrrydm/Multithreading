package Auto;

import Interface.*;
import Exceptions.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Vehicle {
    public static double getAverage(Transport transport) {
        double[] arrPricesOfModels = transport.getArrayPricesOfModels();
        double sumPrice = 0;
        for (double price : arrPricesOfModels) {
            sumPrice += price;
        }
        return sumPrice / transport.getLenModels();
    }

    public static void printNamesModel(Transport transport) {
        String[] arrNamesOfModels = transport.getArrayNamesOfModels();
        for (String name : arrNamesOfModels) {
            System.out.println("Имя модели: " + name);
        }
    }

    public static void printPricesModel(Transport transport) {
        double[] arrPricesOfModels = transport.getArrayPricesOfModels();
        for (double price : arrPricesOfModels) {
            System.out.println("Цена модели: " + price);
        }
    }

    //Запись в байтовый поток
    private static void recordingStream(String strData, DataOutputStream dataOutputStream) throws IOException {
        byte[] bytes = strData.getBytes();
        //чтобы при чтении знать сколько читать
        dataOutputStream.writeInt(bytes.length);//записывает в поток целочисленное значение int
        dataOutputStream.write(bytes);
    }

    public static void outputTransport(Transport v, OutputStream out) throws IOException, NoSuchModelNameException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        recordingStream(v.getVehicle(), dataOutputStream);
        recordingStream(v.getMotoBrand(), dataOutputStream);

        //чтобы при чтении знать сколько элементолв в массиве
        int len = v.getLenModels();
        dataOutputStream.writeInt(len);
        String[] models = v.getArrayNamesOfModels();
        double[] prices = v.getArrayPricesOfModels();
        for (int i = 0; i < v.getLenModels(); i++) {
            dataOutputStream.writeUTF(models[i]);
            dataOutputStream.writeDouble(prices[i]);
        }
    }

    //Чтение из байтового потока
    private static String readingStream(DataInputStream dataInputStream) throws IOException {
        //длина модели
        int length = dataInputStream.readInt();//считывает из потока целочисленное значение int
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = dataInputStream.readByte();//считывает из потока 1 байт
        }
        String name = new String(bytes);
        return name;
    }

    public static Transport inputTransport(InputStream in) throws IOException, DuplicateModelNameException, NoSuchModelNameException {
        Transport transport = null;
        DataInputStream dataInputStream = new DataInputStream(in);

        switch (readingStream(dataInputStream)) {
            case "Car":
                transport = new Car(readingStream(dataInputStream));
                break;
            case "Motorbike":
                transport = new Motorbike(readingStream(dataInputStream));
                break;
        }
        //сколько моделей и цен
        int numModels = dataInputStream.readInt();
        for (int i = 0; i < numModels; i++) {
            String model = dataInputStream.readUTF();
            double price = dataInputStream.readDouble();
            transport.addNewModel(model, price);
        }
        return transport;
    }

    //Записи информации в символьный поток
    public static void writeTransport1(Transport v, Writer out) {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.println(v.getVehicle());
        printWriter.println(v.getMotoBrand());
        printWriter.println(v.getLenModels());
        String[] models = v.getArrayNamesOfModels();
        double[] prices = v.getArrayPricesOfModels();
        for (int i = 0; i < v.getLenModels(); i++) {
            printWriter.println(models[i]);
            printWriter.println(prices[i]);
        }
        printWriter.flush();//сброс буф потока и запись
    }

    //Чтение из символьного потока
    public static Transport readTransport1(Reader in) throws IOException, DuplicateModelNameException, NoSuchModelNameException {
        Transport transport = null;
        BufferedReader bfReader = new BufferedReader(in);//Создает буферный поток ввода символов

        switch (bfReader.readLine()) {
            case "Car":
                transport = new Car(bfReader.readLine());
                break;
            case "Motorbike":
                transport = new Motorbike(bfReader.readLine());
                break;
        }
        int numModels = Integer.parseInt(bfReader.readLine());
        for (int i = 0; i < numModels; i++) {
            String model = bfReader.readLine();
            double price = Double.parseDouble(bfReader.readLine());
            transport.addNewModel(model, price);
        }
        return transport;
    }

    public static Transport createTransport(String brand, int modelLength, Transport transport) {
        Class reflect = transport.getClass();
        try {
            Constructor classConstructor = reflect.getConstructor(String.class, int.class);
            return (Transport) classConstructor.newInstance(brand, modelLength);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public static double getMeanPrice(Transport... arrays) {
        double sum = 0;
        int len = 0;
            for (Transport transport : arrays) {
                sum += getAverage(transport) * transport.getLenModels();
                len += transport.getLenModels();
            }
        return sum / len;
    }

    public static void writeTransport(Transport transport) {
        String[] names = transport.getArrayNamesOfModels();
        double[] prices = transport.getArrayPricesOfModels();
        System.out.printf("\nМарка: %s\n", transport.getMotoBrand());
        System.out.printf("Количество: %o", transport.getLenModels());


        for (int i = 0; i < transport.getLenModels(); i++) {
            System.out.printf("\nНазвание: %s\n",names[i]);
            System.out.printf("Цена: %s\n", prices[i]);
        }
    }

    public static Transport readTransport() throws IOException, DuplicateModelNameException {
        Scanner scanner = new Scanner(System.in);
        Transport transport = null;
        System.out.println("Тип:");
        String type = scanner.nextLine();
        System.out.println("Марка:");
        String brand = scanner.nextLine();
        System.out.println("Количесвто:");
        int length = scanner.nextInt();
        scanner.nextLine();
            switch (type){
                case "Car":
                    transport = new Car(brand,0);
                    break;
                case "Motorbike":
                    transport = new Motorbike(brand,0);
                    break;
                case "Moped":
                    transport = new Moped(brand,0);
                    break;
                case "ATV":
                    transport = new ATV(brand,0);
                    break;
                case "Scooter":
                    transport = new Scooter(brand,0);
                    break;
            }
            for (int i = 0; i < length; i++) {
                System.out.println("Название:");
                String name = scanner.nextLine();
                System.out.println("Цена:");
                double price = scanner.nextDouble();
                scanner.nextLine();
                transport.addNewModel(name, price);
        }
        return transport;
    }
}
