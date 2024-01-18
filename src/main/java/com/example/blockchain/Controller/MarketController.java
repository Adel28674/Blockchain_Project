package com.example.blockchain.Controller;

import com.example.blockchain.AccueilPage;
import com.example.blockchain.HelloApplication;
import com.example.blockchain.View.ProductElement;
import com.example.blockchain.modele.Cryptocurrency;
import com.example.blockchain.modele.Stocking;
import com.example.blockchain.modele.Symbol;
import com.example.blockchain.modele.Value;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MarketController {

    @FXML
    public GridPane grid;

    @FXML
    public Button btnSidebarAccueil;

    @FXML
    public Button btnSidebarWalletManager;

    @FXML
    public Button btnSidebaMarket;

    @FXML
    public Button btnSidebaProfile;

    @FXML
    public VBox container;

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

    public void setData(){
        //ProductElement p = new ProductElement(new Cryptocurrency("ETHERUM", 100.00, "ETHERUM", 150));

        TilePane tile = new TilePane(Orientation.HORIZONTAL);
        tile.setTileAlignment(Pos.CENTER_LEFT);
        tile.setPrefRows(3);
        for (String s : Symbol.cryptoHM.keySet()) {
            Value val = new Cryptocurrency(s, 100.00, s, 1);
            tile.getChildren().add(new ProductElement(val));
        }

        for (String s : Symbol.stockHM.keySet()) {
            Value val = new Stocking(Symbol.stockHM.get(s), 100.00, Symbol.stockHM.get(s), 1, s);
            System.out.println(val.getName());
            tile.getChildren().add(new ProductElement(val));
        }

        container.getChildren().add(tile);
    }
}
