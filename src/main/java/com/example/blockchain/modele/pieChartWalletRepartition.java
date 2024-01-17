package com.example.blockchain.modele;

import javafx.scene.chart.PieChart;

import java.io.IOException;

public class pieChartWalletRepartition {

    public pieChartWalletRepartition(){

    }

    public static PieChart repartionOfCryptoAndStockCollective(Wallet wallet) throws IOException {
        Double partCrypto = wallet.getPartCrypto();
        Double partStock = wallet.getPartStock();

        Double cryptoPercent = 100 * partCrypto / (partCrypto+partStock);
        Double StockPercent = 100 * partStock / (partCrypto+partStock);

        System.out.println(" -cryptoPercent " + cryptoPercent + " \n -StockPercent " + StockPercent);

        PieChart.Data slice1 = new PieChart.Data("Cryptocurrency", cryptoPercent);
        PieChart.Data slice2 = new PieChart.Data("Stocks", StockPercent);

        slice1.setName("Crypto: " + cryptoPercent + "%");
        slice2.setName("Stock: " + StockPercent + "%");



        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(slice1, slice2);
        pieChart.setTitle("Camembert de la repartition de vos actifs");
        return pieChart;
    }
}
