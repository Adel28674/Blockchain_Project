package com.example.blockchain.modele;

public class Cryptocurrency {

    private String currencyName;
    private long price;

    private String symbol;

    public Cryptocurrency(String currencyName, long price, String symbol) {
        this.currencyName = currencyName;
        this.price = price;
        this.symbol = symbol;
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
}
