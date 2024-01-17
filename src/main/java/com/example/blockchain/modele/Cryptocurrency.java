package com.example.blockchain.modele;

import org.json.JSONObject;

import java.io.IOException;

public class Cryptocurrency extends Value{
    public Cryptocurrency(String currencyName, Double price, String symbol, float quantity) {
        super(currencyName, price, symbol, quantity);
    }

    public Double getPrice() throws IOException {
        JSONObject json = BinanceManager.getOneCryptoValueJson(this.getSymbol());
        return json.getDouble("price");
    }

}
