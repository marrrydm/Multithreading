package Auto;

import Exceptions.*;
import Interface.Transport;
import java.util.HashMap;
import java.util.Map;

public class Scooter implements Transport {
    private String brand;
    private HashMap<String, Double> models;
    private String vehicle = "Scooter";

    public Scooter(String brand) {
        this.brand = brand;
        this.models = new HashMap<>();
    }

    public Scooter(String brand, int size) throws DuplicateModelNameException {
        this.brand = brand;
        models = new  HashMap<>();
        for (int i = 0; i < size; i++) {
            models.put("Скутер" + (i + 1)  , 100.0 * (i + 1));
        }
    }

    public String getMotoBrand() {return brand;}

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String[] getArrayNamesOfModels() {
        String[] names = new String[this.models.size()];
        int i = 0;
        for (String key : this.models.keySet()) {
            names[i] = key;
        }
        return names;
    }

    public String getVehicle() {
        return vehicle;
    }

    public double[] getArrayPricesOfModels() {
        double[] prices = new double[this.models.size()];
        int i = 0;
        for (Map.Entry<String, Double> price : models.entrySet()) {
            prices[i] = price.getValue();
            i++;
        }
        return prices;
    }

    public void setPriceModelByName(String modelName, double modelPrice) throws NoSuchModelNameException, DuplicateModelNameException {
        if(modelPrice < 0) throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");
        if(!models.containsKey(modelName)) throw new NoSuchModelNameException("Модель не найдена");
        models.put(modelName,modelPrice);
    }

    public void setNewModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        if (models.containsKey(newName)) throw new DuplicateModelNameException("Такая модель уже есть");
        if (!models.containsKey(oldName)) throw new NoSuchModelNameException("Модель не найдена");
        models.put(newName, models.get(oldName));
        models.remove(oldName);
    }

    public void addNewModel(String modelName, double modelPrice) throws DuplicateModelNameException {
        if (modelPrice < 0) throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");
        if (models.containsKey(modelName)) throw new DuplicateModelNameException("Такая модель уже есть");
        models.put(modelName, modelPrice);
    }

    public void deleteModelByName(String modelName) throws NoSuchModelNameException {
    if (!models.containsKey(modelName)) throw new NoSuchModelNameException("Модель не найдена");
    models.remove(modelName);
    }

    public double getPriceModelByName(String nameModel) throws NoSuchModelNameException {
    if (!models.containsKey(nameModel)) throw new NoSuchModelNameException("Модель не найдена");
    return models.get(nameModel);
    }

    public int getLenModels() {
        return models.size();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Тип объекта:").append(this.getClass().getSimpleName()).append("\n");
        String brand = getMotoBrand();
        stringBuffer.append("Марка: ").append(brand).append("\n");
        for (Map.Entry<String,Double> pair : models.entrySet()){
            stringBuffer.append(pair.getKey()).append(" , ");
            stringBuffer.append(pair.getValue()).append("  ");
            stringBuffer.append("\n");
        }
        return new String(stringBuffer);
    }
}
