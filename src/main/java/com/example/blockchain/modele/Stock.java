package com.example.blockchain.modele;

public class Stock {

    private String stockName;

    private String company;
    private long price;

    private String symbol;

    public Stock(String stockName, String company, long price, String symbol) {
        this.stockName = stockName;
        this.company = company;
        this.price = price;
        this.symbol = symbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
