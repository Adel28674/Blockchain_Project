package com.example.blockchain.modele;

import java.io.IOException;

public class Stocking extends Value{

    private String company;

    public Stocking(String currencyName, Double price, String symbol, float quantity, String company) {
        super(currencyName, price, symbol, quantity);
        this.company = company;

    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getPrice() throws IOException {
        return super.getPrice();
    }

    public String getName(){
        return super.getName();
    }
}
