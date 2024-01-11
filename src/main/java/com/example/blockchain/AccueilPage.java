package com.example.blockchain;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccueilPage {

    public static JSONArray json ;
    @FXML
    public static LineChart<String,Number> chart ;

    public void onBtnClickChartInitializer() throws IOException {
        //URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT");
            URL url = new URL("https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=3d");

        BufferedReader in = null;

        StringBuilder jsontext = new StringBuilder();



        try{
            URLConnection yc = url.openConnection();
            System.out.println("Connected to Binance API");

            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()
                    )
            );
            String inputLine;
            while((inputLine = in.readLine()) != null){
                jsontext.append(inputLine);
                json = new JSONArray(jsontext.toString());
            }
        }
        finally{
            in.close();
        }

        json = new JSONArray(jsontext.toString());


        //System.out.println(json.get("price"));


        final CategoryAxis xAxis = new CategoryAxis();
        // Axe pour les prix
        final NumberAxis yAxis = new NumberAxis();

        // Création du graphique à barres
        LineChart linechart = new LineChart(xAxis, yAxis);
        linechart.setTitle("Historique du Bitcoin");
        linechart.setMinHeight(700);
        linechart.setMinWidth(1100);

        // Série de données
        for (int i = 0; i < json.length()-1; i++) {
            XYChart.Series series = new XYChart.Series();

            JSONArray candle = json.getJSONArray(i);
            JSONArray candle1 = json.getJSONArray(i+1);

            long timestamp = candle.getLong(0);
            //Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(timestamp);

            long timestamp1 = candle1.getLong(0);
            //Timestamp stamp1 = new Timestamp(System.currentTimeMillis());
            Date date1 = new Date(timestamp1);

            series.getData().add(new XYChart.Data(date.toString(), candle.getDouble(2)));
            series.getData().add(new XYChart.Data(date1.toString(), candle1.getDouble(2)));

            linechart.getData().add(series);
        }

        Group root = new Group(linechart);

        //Creating a scene object
        Scene scene = new Scene(root, 1200, 800);

        Stage stage = new Stage();

        //Setting title to the Stage
        stage.setTitle("Line Chart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();



    }



}

