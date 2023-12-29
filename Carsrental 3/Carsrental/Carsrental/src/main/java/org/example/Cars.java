package org.example;

public class Cars {

    private String marka;
    private String model;
    private String vin;
    private Type type;

    public Cars(String marka, String model, String vin, Type type) {
        this.marka = marka;
        this.model = model;
        this.vin = vin;
        this.type = type;


    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getVin() {
        return vin;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", vin='" + vin + '\'' +
                ", type=" + type +
                '}';
    }
}

