package com.example.blockchain;

import com.example.blockchain.Controller.MarketController;
import com.example.blockchain.Controller.ProfilPageController;
import com.example.blockchain.modele.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;

import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    @FXML
    public Label bitcoinChange;

    @FXML
    public Label EthChange;

    @FXML
    public Label LtcChange;

    @FXML
    public Label neoChange;

    @FXML
    public Label bnbChange;

    @FXML
    public VBox pieChartContainer;

    @FXML
    public PieChart pieChartRepartitionIndividuals;

    @FXML
    public Button btnSidebarAccueil;

    @FXML
    public Button btnSidebarWalletManager;

    @FXML
    public Button btnSidebaMarket;

    @FXML
    public Button btnSidebaProfile;

    @FXML
    private Label username;


    public Double previousValue = 0.0;

    public XYChart.Series<String, Number> series = new XYChart.Series<>();

    public boolean firstChangeWallet = true;

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private Timeline timeline;



    public void onBtnClickChartInitializer() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("BITCOIIIIIIN", "BITCOIN");

        c.show();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        username.setText(CurrentUser.userConnected.getUserLogin());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Données en temps réel");
        series.setNode(new Circle(3));


        lineChartWallet.getData().add(series);


        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

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

                Wallet wal = CurrentUser.userConnected.getWallets().get(UUID.fromString(comboBoxWallets.getValue()));

                if (firstChangeWallet==true){
                    System.out.println("CHANGE");
                    firstChangeWallet = false;

                    try {
                        PieChart pie = pieChartWalletRepartition.repartionOfCryptoAndStockCollective(wal);
                        pieChartContainer.getChildren().clear();
                        pieChartContainer.getChildren().add(pie);
                        pieChartWalletRepartition.repartionOfCryptoAndStockIndividuals(wal, pieChartRepartitionIndividuals);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else {
                    System.out.println("CHANGE");
                    series.getData().clear();
                    Platform.runLater(()-> {
                        lineChartWallet = WalletChart.createRealTimeLineChart();
                    });


                    try {
                        PieChart pie = pieChartWalletRepartition.repartionOfCryptoAndStockCollective(wal);
                        pieChartContainer.getChildren().clear();
                        pieChartContainer.getChildren().add(pie);
                        pieChartWalletRepartition.repartionOfCryptoAndStockIndividuals(wal, pieChartRepartitionIndividuals);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });




        this.changeLabelCrypto();


    }

        public void changeLabelCrypto(){
            Runnable tache = new Runnable() {
                @Override
                public void run() {
                    try {

                        String bitcoinPrice = BinanceManager.getOneCryptoValueJson("BITCOIN").getString("price");
                        String ethPrice = BinanceManager.getOneCryptoValueJson("ETHERUM").getString("price");
                        String ltcPrice = BinanceManager.getOneCryptoValueJson("LITECOIN").getString("price");
                        String neoPrice = BinanceManager.getOneCryptoValueJson("NEO").getString("price");
                        String bnbPrice = BinanceManager.getOneCryptoValueJson("BNB").getString("price");

                        Platform.runLater(() -> {
                            bitcoinChange.setText(bitcoinPrice);
                            EthChange.setText(ethPrice);
                            LtcChange.setText(ltcPrice);
                            neoChange.setText(neoPrice);
                            bnbChange.setText(bnbPrice);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            scheduler.scheduleAtFixedRate(tache, 0, 1, TimeUnit.SECONDS);

        }

    public void onBitcoinLabelClicked() throws IOException {
        Platform.runLater(() -> {
            try {
                ChartCalculationCrypto c = new ChartCalculationCrypto("CandlestickChart du prix du bitcoin", "BITCOIN");
                c.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void onEthLabelClicked() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("CandlestickChart du prix de l'Etherum", "ETHERUM");
        c.show();
    }


    public void onLtcLabelClicked() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("CandlestickChart du prix du Litecoin", "LITECOIN");
        c.show();
    }


    public void onNeoLabelClicked() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("CandlestickChart du prix du neo", "NEO");
        c.show();
    }


    public void onBnbLabelClicked() throws IOException {
        ChartCalculationCrypto c = new ChartCalculationCrypto("CandlestickChart du prix du BNB", "BNB");
        c.show();
    }


    public void onbtnSidebarAccueilClicked() throws IOException {
        Stage st = (Stage) btnSidebarAccueil.getScene().getWindow();
        st.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueilPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        AccueilPage accueilPage  = fxmlLoader.getController();
        accueilPage.setData();
        stage.setTitle("Accueil!");
        stage.setScene(scene);
        stage.show();
    }

    public void onbtnSidebarWalletManagerClicked() throws IOException {
        Stage st = (Stage) btnSidebarAccueil.getScene().getWindow();
        st.close();

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("wallet-manager.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Wallet Manager!");
        stage.setScene(scene);
        stage.show();
    }

    public void onbtnSidebaMarketClicked() throws IOException {

        stopGetCryptoRealTime();
        timeline.stop();

        Stage stage1 = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("market.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        MarketController m = fxmlLoader.getController();
        m.setData();

        stage1.setTitle("Market!");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) btnSidebarAccueil.getScene().getWindow();
        stage.close();
    }

    public void onbtnProfileClicked() throws IOException {

        stopGetCryptoRealTime();
        timeline.stop();

        Stage stage1 = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profil-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        ProfilPageController p = fxmlLoader.getController();
        p.setData();

        stage1.setTitle("Votre Profil!");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) btnSidebarAccueil.getScene().getWindow();
        stage.close();
    }

    public static void stopGetCryptoRealTime() {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                scheduler.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            scheduler = Executors.newScheduledThreadPool(1);
            System.out.println("Planificateur arrêté.");
        }
    }






}

