package com.example.blockchain.modele;

public class Stocking extends Value{

    private String company;



    public Stocking(String currencyName, long price, String symbol, float quantity, String company) {
        super(currencyName, price, symbol, quantity);
        this.company = company;

    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
