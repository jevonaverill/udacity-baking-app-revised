package com.udacity.jevonaverill.udacitybakingapprevised.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class Ingredient implements Serializable {

    private double quantity;
    private String measure;

    @SerializedName("ingredient")
    private String name;

    public Ingredient() {
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "\nIngredient {" +
                "\n\tquantity=" + quantity +
                ", \n\tmeasure='" + measure + '\'' +
                ", \n\tname='" + name + '\'' +
                '}';
    }

}
