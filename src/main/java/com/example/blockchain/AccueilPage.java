package com.example.blockchain;

import com.example.blockchain.modele.ChartCalculationCrypto;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AccueilPage {

    public static JSONArray json ;
    @FXML
    public static LineChart<String,Number> chart ;

    public void onBtnClickChartInitializer() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("BITCOIIIIIIN", "BITCOIN");

        c.show();



    }



}

