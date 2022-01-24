package Auto;

import Exceptions.DuplicateModelNameException;
import Exceptions.ModelPriceOutOfBoundsException;
import Exceptions.NoSuchModelNameException;
import Interface.Transport;

import java.io.Serializable;
import java.util.Arrays;

public class Motorbike implements Transport {
    private static final long serialVersionUID = 1;
    private String motoBrand;
    private int size = 0;
    private String vehicle = "Motorbike";

    private class Model implements Serializable,Cloneable {
        String name = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        @Override
        public String toString() {
            return getNameModel() + " , " + getPriceModel();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Model)) return false;
            if (obj instanceof Model) {
                Model model = (Model) obj;
                return model.name.equals(name) && model.price == price;
            }
            return false;
        }
        @Override
        public int hashCode() {
            int hash = (int) price;
            hash = 10 * hash + (name != null ? name.hashCode() : 0);
            return hash;
        }
        @Override
        public Object clone() {
            Model model = null;
            try {
                model = (Model) super.clone();
            } catch (CloneNotSupportedException exception) {
                return model;
            }
            return model;
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
            this.price = price;
        }

        public Model() {
        }

        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    public String getVehicle() {
        return vehicle;
    }

    public int getLenModels() {
        return size;
    }

    public String getMotoBrand() {
        return motoBrand;
    }

    public void setMotoBrand(String motoBrand) {
        this.motoBrand = motoBrand;
    }

    private Model head = new Model();

    {
        head.prev = head;
        head.next = head;
    }

    public Motorbike(String motoBrand, int length) throws DuplicateModelNameException {
        setMotoBrand(motoBrand);
        for (int i = 0; i < length; i++) {
            addNewModel("Мотоцикл" + i, 100 * (i + 1));
        }
    }

    public void addNewModel(String modelName, double modelPrice) throws DuplicateModelNameException {
        if (modelPrice < 0)
            throw new ModelPriceOutOfBoundsException("Цена модели не может быть отрицательной");
        Model model = head.next;
        while (model != head) {
            if (model.getNameModel().equals(modelName))
                throw new DuplicateModelNameException("Такая модель уже есть");
            model = model.next;
        }
        Model newModel = new Model(modelName, modelPrice);
        newModel.next = head;
        newModel.prev = head.prev;
        head.prev.next = newModel;
        head.prev = newModel;
        size++;
    }

    public void setNewModelName(String oldName, String newName)
            throws DuplicateModelNameException, NoSuchModelNameException {
        Model p = head.next;
        while (p.next != head) {
            if (p.getNameModel().equals(newName))
                throw new DuplicateModelNameException("Такая модель уже есть");
            p = p.next;
        }
        Model q = head.next;
        while (q.next != head && !q.getNameModel().equals(oldName)) {
            q = q.next;
        }
        if (q == head)
            throw new NoSuchModelNameException("Такого имени нет");
        q.setNameModel(newName);
    }


    public String[] getArrayNamesOfModels() {
        int i = 0;
        String[] names = new String[getLenModels()];
        Model model = head.next;
        while (model != head) {
            names[i] = model.name;
            i++;
            model = model.next;
        }
        return names;
    }

    public double[] getArrayPricesOfModels() {
        double[] arrPricesOfModels = new double[getLenModels()];
        int i = 0;
        Model model = head.next;
        while (model != head) {
            arrPricesOfModels[i] = model.getPriceModel();
            model = model.next;
            i++;
        }
        return arrPricesOfModels;
    }

    public double getPriceModelByName(String modelName)
            throws NoSuchModelNameException {
        Model model = head.next;
        while (model != head && !model.getNameModel().equals(modelName)) {
            model = model.next;
        }
        if (model.equals(head)) throw new NoSuchModelNameException("Модели с таким именем нет");
        return model.getPriceModel();
    }

    public void setPriceModelByName(String modelName, double modelPrice) throws NoSuchModelNameException {
        if (modelPrice < 0)
            throw new ModelPriceOutOfBoundsException("Цена модели должна быть положительным числом");
        Model model = head.next;
        while (model != head && !model.getNameModel().equals(modelName)) {
            model = model.next;
        }
        if (model == head) throw new NoSuchModelNameException("Такой модели нет");
        model.setPriceModel(modelPrice);
    }

    public void deleteModelByName(String modelName) throws NoSuchModelNameException {
        Model model = head.next;
        while (model != head && !model.getNameModel().equals(modelName)) {
            model = model.next;
        }
        if (model.equals(head)) throw new NoSuchModelNameException("Такой модели нет");
        model.next.prev = model.prev;
        model.prev.next = model.next;
        size--;
    }

    public Motorbike(String brand) {
        this.motoBrand = brand;
    }

   @Override
   public String toString() {
       StringBuffer stringBuffer = new StringBuffer();
       String brand = getMotoBrand();
       stringBuffer.append("Марка: ").append(brand).append("\n");
       int size = getLenModels();
       stringBuffer.append("Количество: ").append(size).append("\n");
       stringBuffer.append("Модели: " + "\n");

       Model temp = head.next;
       while (temp != head) {
           stringBuffer.append(temp).append("\n");
           temp = temp.next;
       }
       return stringBuffer.toString();
   }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Transport)) return false;
        if (obj instanceof Transport) {
            Transport transport = (Transport) obj;
            return this.getMotoBrand().equals(transport.getMotoBrand()) &&
                    Arrays.equals(this.getArrayNamesOfModels(), transport.getArrayNamesOfModels()) &&
                    Arrays.equals(this.getArrayPricesOfModels(), transport.getArrayPricesOfModels());
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash  = motoBrand.hashCode() ;
        Model temp = head.next;
        while (temp != head) {
            hash = hash * 10 * temp.hashCode();
            temp = temp.next;
        }
        return hash;
    }
    
    @Override
    public Object clone(){
        Motorbike moto =null;
        try{
            moto = (Motorbike) super.clone();
            moto.head = (Model)head.clone();
            Model cloned = moto.head;
            Model temp = head.next;
           //
            while (temp != head) {
                cloned.next=(Model) temp.clone();
                cloned.next.prev =cloned;
                temp = temp.next;
                cloned = cloned.next;
            }
            cloned.next=moto.head;
            moto.head.prev=cloned;
            return moto;
        } catch (CloneNotSupportedException exception) {
            return moto;
        }
    }
}



