package com.example.blockchain.View;

import com.example.blockchain.Controller.WalletManagerController;
import com.example.blockchain.HelloApplication;
import com.example.blockchain.modele.Value;
import com.example.blockchain.modele.Wallet;
import com.example.blockchain.modele.WalletChart;
import com.example.blockchain.modele.pieChartWalletRepartition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WalletBox extends VBox {

    public WalletBox(Wallet wallet){
        //Cette page va lister tout les wallet d'un investisseur
        this.getStylesheets().add("@css/walletboxcss.css");

        this.getStyleClass().add("wallet-box");

        Label nameLabel = new Label("Nom du portefeuille: " + wallet.getToken());
        nameLabel.getStyleClass().add("wallet-label");

        Label capitalLabel = new Label("Capital: " + wallet.getCapital());
        capitalLabel.getStyleClass().add("wallet-label");

        Label tokenLabel = new Label("Token: " + wallet.getToken());
        tokenLabel.getStyleClass().add("wallet-label");

        HBox hImpot = new HBox();
        Label impot = new Label("Si vous vendez tous vos biens tout de suite, Vous paierez : ");
        impot.getStyleClass().add("wallet-label");
        Label montantImpot = null;
        try {
            montantImpot = new Label(calculMontantImpot(wallet.getSumValues()).toString() + " d'impots");
            montantImpot.getStyleClass().add("wallet-label");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hImpot.getChildren().addAll(impot,montantImpot);

        HBox hRetraite = new HBox();
        Label retraite = new Label("Nous estimons qu'il vous restera ");
        retraite.getStyleClass().add("wallet-label");
        Label montantRetraite = null;
        try {
            montantRetraite = new Label(calculRetraite(wallet.getSumValues()).toString() + " à votre retraite");
            montantRetraite.getStyleClass().add("wallet-label");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hRetraite.getChildren().addAll(retraite,montantRetraite);



        Button buttonDetails = new Button("Show Details");

        buttonDetails.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Buy - " + wallet.getToken());
                alert.setHeaderText("Details of Your Wallet");

                TabPane tabPane = new TabPane();

                ObservableList<Value> valeurs = FXCollections.observableArrayList(wallet.listValues.values());

                TableView<Value> tableView = new TableView<>();

                TableColumn<Value, String> nameColumn = new TableColumn<>("Name");
                nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));


                TableColumn<Value, String> symbolColumn = new TableColumn<>("Symbol");
                symbolColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSymbol()));

                TableColumn<Value, Double> priceColumn = new TableColumn<>("Price");
                priceColumn.setCellValueFactory(cellData -> {
                    try {
                        return new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                TableColumn<Value, Float> quantityColumn = new TableColumn<>("Quantity");
                quantityColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantity()));





                tableView.getColumns().add(nameColumn);
                tableView.getColumns().add(symbolColumn);
                tableView.getColumns().add(priceColumn);
                tableView.getColumns().add(quantityColumn);

                tableView.setItems(valeurs);


                Tab tab1 = new Tab("TableView Of the Values in your Wallet");
                tab1.setContent(tableView);
                tab1.setClosable(false);


                tabPane.getTabs().add(tab1);

                alert.getDialogPane().setContent(tabPane);


                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        alert.close();
                    }
                    if (response == ButtonType.CANCEL){
                        alert.close();
                    }
                });
            }
        });

        Button buttonPieChartRepartition = new Button("Répartition");
        buttonPieChartRepartition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage stage = new Stage();
                try {
                    PieChart pie = pieChartWalletRepartition.repartionOfCryptoAndStockCollective(wallet);
                    Scene scene = new Scene(pie, 900, 600);
                    stage.setTitle("PieChart!");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        HBox hBox = new HBox(buttonDetails, buttonPieChartRepartition);

        this.getChildren().addAll(nameLabel, capitalLabel, tokenLabel,hImpot, hRetraite,hBox);
        this.setPadding(new Insets(15));
    }

    public Double calculMontantImpot(Double patrimoine){
        return patrimoine*0.3;
    }

    public Double calculRetraite(Double patrimoine){
        return patrimoine*0.347791548;
    }


}
