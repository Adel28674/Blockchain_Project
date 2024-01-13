package com.example.blockchain.modele;

import yahoofinance.Stock;

public class Stocking extends Value{

    private String company;

    private Stock stock ;

    public Stocking(String currencyName, long price, String symbol, float quantity, String company) {
        super(currencyName, price, symbol, quantity);
        this.company = company;
        stock = new Stock(symbol);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
