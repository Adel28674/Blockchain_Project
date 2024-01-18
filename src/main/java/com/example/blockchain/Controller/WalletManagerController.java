package com.example.blockchain.Controller;

import com.example.blockchain.AccueilPage;
import com.example.blockchain.HelloApplication;
import com.example.blockchain.View.WalletBox;
import com.example.blockchain.modele.CurrentUser;
import com.example.blockchain.modele.Wallet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class WalletManagerController {

    @FXML
    public Button btnSidebarAccueil;

    @FXML
    public Button btnSidebarWalletManager;

    @FXML
    public Button btnSidebaMarket;

    @FXML
    public Button btnSidebaProfile;

    @FXML
    public TilePane tilePane;

    @FXML
    private Button btn_addWallet;

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
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/blockchain/css/walletboxcss.css").toExternalForm());
        WalletManagerController walCon = fxmlLoader.getController();
        walCon.setData();

        stage.setTitle("Wallet Manager!");
        stage.setScene(scene);
        stage.show();
    }

    public void onbtnSidebaMarketClicked() throws IOException {


        Stage stage1 = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("market.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        MarketController m = fxmlLoader.getController();
        m.setData();

        stage1.setTitle("Market!");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) btnSidebarAccueil.getScene().getWindow();
        stage.close();
    }

    public void onbtnProfileClicked() throws IOException {


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


    public void btn_addWalletClicked(){
        CurrentUser.userConnected.createWallet();
        this.refresh();
    }


    public void refresh(){
        tilePane.getChildren().clear();
        Object[] values = CurrentUser.userConnected.getWallets().keySet().toArray();
        for (int i = 0; i < values.length; i++) {
            Wallet wallet = CurrentUser.userConnected.getWallets().get(values[i]);
            WalletBox walBox = new WalletBox(wallet);
            tilePane.getChildren().add(walBox);
        }
    }


    public void btn_CloneClicked(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove a Wallet");
        alert.setHeaderText("Chose The Wallet you want to remove");

        VBox dialogPaneContent = new VBox();
        ComboBox comboBoxWallets = new ComboBox<>();


        comboBoxWallets.getItems().clear();
        if (CurrentUser.userConnected.getWallets()!=null){
            for (Map.Entry mapentry : CurrentUser.userConnected.getWallets().entrySet()) {
                comboBoxWallets.getItems().add(String.valueOf((UUID)mapentry.getKey()));
            }
        }

        dialogPaneContent.getChildren().add(comboBoxWallets);

        alert.getDialogPane().setContent(dialogPaneContent);


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && comboBoxWallets.getValue()!=null) {
                CurrentUser.userConnected.cloneWallet(UUID.fromString((String)comboBoxWallets.getValue()));
                this.refresh();
                alert.close();
            }
            if (response == ButtonType.CANCEL){
                alert.close();
            }
        });
    }
    public void btn_RemoveWalletClicked(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove a Wallet");
        alert.setHeaderText("Chose The Wallet you want to remove");

        VBox dialogPaneContent = new VBox();
        ComboBox comboBoxWallets = new ComboBox<>();


        comboBoxWallets.getItems().clear();
        if (CurrentUser.userConnected.getWallets()!=null){
            for (Map.Entry mapentry : CurrentUser.userConnected.getWallets().entrySet()) {
                comboBoxWallets.getItems().add(String.valueOf((UUID)mapentry.getKey()));
            }
        }

        dialogPaneContent.getChildren().add(comboBoxWallets);

        alert.getDialogPane().setContent(dialogPaneContent);


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && comboBoxWallets.getValue()!=null) {
                CurrentUser.userConnected.removeWallet(UUID.fromString((String)comboBoxWallets.getValue()));
                alert.close();
                this.refresh();
            }
            if (response == ButtonType.CANCEL){
                alert.close();
            }
        });


    }

    public void setData(){
        Object[] values = CurrentUser.userConnected.getWallets().keySet().toArray();
        for (int i = 0; i < values.length; i++) {
            Wallet wallet = CurrentUser.userConnected.getWallets().get(values[i]);
            WalletBox walBox = new WalletBox(wallet);
            tilePane.getChildren().add(walBox);
        }
    }
}
