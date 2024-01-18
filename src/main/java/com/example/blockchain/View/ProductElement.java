package com.example.blockchain.View;

import com.example.blockchain.modele.Cryptocurrency;
import com.example.blockchain.modele.CurrentUser;
import com.example.blockchain.modele.Value;
import com.example.blockchain.modele.Wallet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ProductElement extends VBox {

    private Cryptocurrency chosenCrypto;

    private Wallet chosenWallet;

    private HashMap<String, Value> hmVal;
    public ProductElement(Value value){

        this.getClass().getResource("/com/example/blockchain/css/productElementcss.css").toExternalForm();
        this.getStyleClass().add("product-Element");

        Label nomValeur = new Label();
        nomValeur.setText(value.getName());

        nomValeur.getStyleClass().add("product-element-name");

        Label prixUnitaire = new Label();
        try {
            prixUnitaire.setText(value.getPrice().toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prixUnitaire.getStyleClass().add("product-element-price");

        Button buyBtn  = new Button("Buy");

        nomValeur.getStyleClass().add("button");

        buyBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Buy - " + value.getName());
                alert.setHeaderText("Entrez Ce que vous voulez comme montant");


                VBox dialogPaneContentPayWithCapital = new VBox();
                Label quantityLab = new Label("Entrez la quantité voulue");
                TextField quantity = new TextField();
                Label labelPrixTot = new Label();

                buyBtn.getStyleClass().add("button");
                nomValeur.getStyleClass().add("label");
                prixUnitaire.getStyleClass().add("label");



                quantity.setOnKeyTyped(event1 -> {
                    if (!quantityLab.getText().matches("") && quantity.getText().matches("-?\\d*(\\.\\d*)?") && Double.valueOf(quantity.getText())>=0){
                        try {
                            Double val = (Double)(value.getPrice()*(Double.valueOf(quantity.getText())));
                            labelPrixTot.setText(val.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        labelPrixTot.setStyle("-fx-text-fill: red;");
                        labelPrixTot.setText("Err -> Ne mettez que des chiffres");

                    }
                });

                dialogPaneContentPayWithCapital.getChildren().addAll(quantityLab, quantity,labelPrixTot);
                Tab tab1 = new Tab("Capital");
                tab1.setContent(dialogPaneContentPayWithCapital);
                tab1.setClosable(false);


                VBox dialogPaneContentPayWithCrypto = new VBox();
                Label cryptoLab = new Label("Choisissez la crypto que vous voulez utilisez pour payer");
                ObservableList<UUID> ItemsWallet = FXCollections.observableArrayList(CurrentUser.userConnected.getWallets().keySet());
                System.out.println(ItemsWallet.toString());
                Label res = new Label();

                ComboBox<UUID> comboBoxWallets = new ComboBox<>(ItemsWallet);
                comboBoxWallets.setPromptText("--Choisir un Portefeuille--");

                Label labCrypt = new Label("Veuillez d'abord selectionné le wallet à utiliser.\n Puis Choisissez la crypto que vous voulez utilisé pour payer");
                ComboBox<String> comboBoxcrypto = new ComboBox<>();
                comboBoxcrypto.setPromptText("--Choisir une crypto--");


                comboBoxWallets.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        chosenWallet = CurrentUser.userConnected.getWallets().get(newValue);
                        hmVal = (CurrentUser.userConnected.getWallets().get(newValue).listValues);
                        ObservableList<String> ItemsCrypto = FXCollections.observableArrayList(hmVal.keySet());
                        comboBoxcrypto.setItems(ItemsCrypto);
                    }
                });

                Label labelErrChoseCrypto = new Label("Vous devez payer avec une crypto-monnaies");
                labelErrChoseCrypto.setStyle("-fx-text-fill: red;");
                labelErrChoseCrypto.setVisible(false);

                comboBoxcrypto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
                    if (hmVal !=null && hmVal.get(newValue) instanceof Cryptocurrency){
                        chosenCrypto = (Cryptocurrency) hmVal.get(newValue);
                        labelErrChoseCrypto.setVisible(false);
                    }else{
                        labelErrChoseCrypto.setVisible(true);
                    }
                        });



                TextField quantity2 = new TextField();
                quantity2.setPromptText("Combien de Valeurs vous voulez");

                Label labelErrQuantity = new Label();
                labelErrQuantity.setVisible(false);

                quantity2.setOnKeyTyped(event1 -> {
                    if (!quantity2.getText().matches("") && quantity2.getText().matches("-?\\d*(\\.\\d*)?") && Double.valueOf(quantity2.getText())>=0){
                        labelErrQuantity.setVisible(false);
                    }else{
                        labelErrQuantity.setVisible(true);
                        labelErrQuantity.setStyle("-fx-text-fill: red;");
                        labelErrQuantity.setText("Err -> Ne mettez que des chiffres");

                    }
                });


                dialogPaneContentPayWithCrypto.getChildren().addAll(cryptoLab, comboBoxWallets, labCrypt,comboBoxcrypto, labelErrChoseCrypto, quantity2,labelErrQuantity);


                Tab tab2 = new Tab("Cryptocurrency");
                tab2.setContent(dialogPaneContentPayWithCrypto);
                tab2.setClosable(false);


                TabPane tab = new TabPane();

                tab.getTabs().addAll(tab1, tab2);
                alert.getDialogPane().setContent(tab);


                quantity.getStyleClass().add("text-field");
                comboBoxWallets.getStyleClass().add("combo-box");
                comboBoxcrypto.getStyleClass().add("combo-box");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        Tab selectedTab = tab.getSelectionModel().getSelectedItem();
                        if (selectedTab == tab1) {
                            String userInput = quantity.getText();
                            System.out.println("Paiement avec capital: " + userInput);
                        } else if (selectedTab == tab2) {

                            System.out.println("Paiement avec crypto: " + chosenCrypto.getName());
                        }
                    }
                    if (response == ButtonType.CANCEL){
                        alert.close();
                    }
                });
            }

        });
        this.getChildren().addAll(nomValeur,prixUnitaire, buyBtn);
    }

}
