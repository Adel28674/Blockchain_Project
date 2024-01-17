package com.example.blockchain.Controller;

import com.example.blockchain.AccueilPage;
import com.example.blockchain.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfilPageController {

    @FXML
    public Button btnSidebarAccueil;

    @FXML
    public Button btnSidebarWalletManager;

    @FXML
    public Button btnSidebaMarket;

    @FXML
    public Button btnSidebaProfile;

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
        Stage stage = (Stage) btnSidebarAccueil.getScene().getWindow();
        stage.close();

        Stage stage1 = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("market.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage1.setTitle("Market!");
        stage1.setScene(scene);
        stage1.show();
    }
}
