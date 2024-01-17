package com.example.blockchain.modele;

import javafx.animation.AnimationTimer;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.Line;

public class WalletChart {

    public static LineChart<String, Number> createRealTimeLineChart() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Temps");
        yAxis.setLabel("Valeur");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Graphique de votre portefeuille en temps réel");

        return lineChart;
    }


}
