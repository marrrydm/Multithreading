package Auto;

import java.util.ArrayList;
import java.util.Arrays;
import Exceptions.*;
import Interface.Transport;

public class ATV implements Transport {
    private String brand;
    private ArrayList<Model> models;
    private String vehicle = "ATV";

    public ATV(String brand) {
        this.brand = brand;
        models = new ArrayList<>();
    }

    public ATV(String brand, int size){
        this.brand = brand;
        models = new ArrayList<>(size);
        for (int i = 0;i < size;i++){
            models.add(new Model("Квадроцикл" + (i + 1)  , 100.0 * (i + 1)));
        }
    }
    private class Model  {

        private String name;
        private double price;

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getNameModel() {
            return name;
        }

        public void setNameModel(String name) {
            this.name = name;
        }

        public double getPriceModel() {
            return price;
        }

        public void setPriceModel(double price) {
            if (price < 0)
                throw new ModelPriceOutOfBoundsException("Цена модели должна быть положительным числом");
            this.price = price;
        }
        public double getPrice() {
            return price;
        }

        public String toString()
        {
            return  getNameModel() + " , "  + getPriceModel();
        }
    }

    public String getMotoBrand() {return brand;}

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String[] getArrayNamesOfModels() {
        String[] names = new String[models.size()];
        for (int i = 0;i < models.size();i++) {
            names[i] = models.get(i).getNameModel();
        }
        return names;
    }

    public String getVehicle() {
        return vehicle;
    }

    public double[] getArrayPricesOfModels() {
        double[] prices = new double[models.size()];
        for (int i = 0;i < models.size();i++) {
            prices[i] = models.get(i).getPriceModel();
        }
        return prices;
    }

    public void setPriceModelByName(String modelName, double modelPrice) throws NoSuchModelNameException {
        if(modelPrice < 0) throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");
        if(!Arrays.asList(getArrayNamesOfModels()).contains(modelName)) throw new NoSuchModelNameException("Модель не найдена");
        models.get(Arrays.asList(getArrayNamesOfModels()).indexOf(modelName)).setPriceModel(modelPrice);
    }

    public void setNewModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        models.get(Arrays.asList(getArrayNamesOfModels()).indexOf(oldName)).setNameModel(newName);
        if(Arrays.asList(getArrayNamesOfModels()).contains(oldName)) throw new DuplicateModelNameException("Такая модель уже есть");
        if(!Arrays.asList(getArrayNamesOfModels()).contains(newName)) throw new NoSuchModelNameException("Модель не найдена");
    }

    public void addNewModel(String modelName, double modelPrice) throws DuplicateModelNameException {
        if (modelPrice < 0) throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");
        for (Model model : models){
            if(model.getNameModel().equals(modelName)) throw new DuplicateModelNameException("Такая модель уже есть");
        }
        models.add(new Model(modelName, modelPrice));
    }

    public void deleteModelByName(String modelName) throws NoSuchModelNameException {
        if(!Arrays.asList(getArrayNamesOfModels()).contains(modelName)) throw new NoSuchModelNameException("Модель не найдена");
        models.remove(Arrays.asList(getArrayNamesOfModels()).indexOf(modelName));
    }

    public double getPriceModelByName(String nameModel) throws NoSuchModelNameException {
        if(!Arrays.asList(getArrayNamesOfModels()).contains(nameModel)) throw new NoSuchModelNameException("Модель не найдена");
        return models.get(Arrays.asList(getArrayNamesOfModels()).indexOf(nameModel)).getPriceModel();
    }

    public int getLenModels() {
        return models.size();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Тип объекта:").append(this.getClass().getSimpleName()).append("\n");
        String brand = getMotoBrand();
        stringBuffer.append("Марка: ").append(brand).append("\n");
        for (Model model : models){
            stringBuffer.append(model.getNameModel()).append(" , ");
            stringBuffer.append(model.getPriceModel()).append("  ");
            stringBuffer.append("\n");
        }
        return new String(stringBuffer);
    }
}
