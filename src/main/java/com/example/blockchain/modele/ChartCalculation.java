package com.example.blockchain.modele;


import com.binance.api.client.domain.market.CandlestickInterval;
import com.example.blockchain.api.examples.CandlesticksCacheExample;

public class ChartCalculation   {
    public static void main(String[] args) {
        new CandlesticksCacheExample("ETHBTC", CandlestickInterval.ONE_MINUTE);    }
}
