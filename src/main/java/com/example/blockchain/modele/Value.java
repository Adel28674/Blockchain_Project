package com.example.blockchain.modele;

public class Value {

    private String currencyName;
    private long price;

    private String symbol;
    private float quantity;


    public Value(String currencyName, long price, String symbol, float quantity) {
        this.currencyName = currencyName;
        this.price = price;
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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
