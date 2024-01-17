package com.example.blockchain;

import com.example.blockchain.modele.ChartCalculationCrypto;
import com.example.blockchain.modele.CurrentUser;
import com.example.blockchain.modele.Wallet;
import com.example.blockchain.modele.WalletChart;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.control.Label;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.*;

public class AccueilPage implements Initializable {

    public static JSONArray json ;
    @FXML
    public LineChart<String,Number> chart ;

    @FXML
    public Label WalletValue;

    @FXML
    public ComboBox<String> comboBoxWallets;
    @FXML
    public LineChart<String,Number> lineChartWallet = WalletChart.createRealTimeLineChart();

    public Double previousValue = 0.0;

    public XYChart.Series<String, Number> series = new XYChart.Series<>();


    public void onBtnClickChartInitializer() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("BITCOIIIIIIN", "BITCOIN");

        c.show();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Données en temps réel");
        series.setNode(new Circle(3));


        lineChartWallet.getData().add(series);


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            String selectedValue = comboBoxWallets.getValue();
            if (selectedValue != null) {
                Task<Double> task = new Task<Double>() {
                    @Override
                    protected Double call() throws IOException {

                        System.out.println("La valeur sélectionnée est : " + selectedValue);
                        CurrentUser.userConnected.addCapital(UUID.fromString(selectedValue),500.0);
                        return CurrentUser.userConnected.getWallets().get(UUID.fromString(selectedValue)).getSumValues();
                    }

                    @Override
                    protected void succeeded() {
                        Double currentValue = getValue();
                        WalletValue.setText(currentValue.toString());

                        long time = System.currentTimeMillis();
                        series.getData().add(new XYChart.Data<>(String.valueOf(String.valueOf(time)), currentValue));
                        int itemCount = series.getData().size();
                        if (itemCount > 20) {
                            series.getData().remove(0);
                        }

                        if (currentValue > previousValue) {
                            WalletValue.setStyle("-fx-text-fill: green;");

                        } else {
                            WalletValue.setStyle("-fx-text-fill: red;");
                        }

                        previousValue = currentValue; // Mise à jour de la valeur précédente
                    }
                };

                new Thread(task).start();
            } else {
                // Aucune valeur sélectionnée, ne faites rien
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    public void setData() {
        comboBoxWallets.getItems().clear();
        if (CurrentUser.userConnected.getWallets()!=null){
            for (Map.Entry mapentry : CurrentUser.userConnected.getWallets().entrySet()) {
                comboBoxWallets.getItems().add(String.valueOf((UUID)mapentry.getKey()));
            }
        }

        comboBoxWallets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("CHANGE");
                series.getData().removeAll();
            }
        });

    }
}

