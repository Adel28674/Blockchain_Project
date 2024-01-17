package com.example.blockchain.modele;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Value implements Serializable {

    private String name;
    private Double price;

    private String symbol;
    private float quantity;


    public Value(String currencyName, Double price, String symbol, float quantity) {
        this.name = currencyName;
        this.price = price;
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() throws IOException {
        return this.price;

    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }


}
