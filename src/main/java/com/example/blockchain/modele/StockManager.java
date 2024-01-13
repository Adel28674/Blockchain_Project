package com.example.blockchain.modele;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

public class StockManager {
    public static void main(String[] args) throws IOException {
        Stock stock = null;
        try{
            stock = YahooFinance.get("TSLA");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();


        stock.print();
    }

}
