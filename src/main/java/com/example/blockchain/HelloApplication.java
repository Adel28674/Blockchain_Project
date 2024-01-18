package com.example.blockchain;

import com.example.blockchain.modele.ConnectionToDB;
import com.example.blockchain.modele.CurrentUser;
import com.example.blockchain.modele.Investor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.blockchain.modele.ChartCalculationStock.graphique;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void stop() throws Exception {

        Investor investor = CurrentUser.userConnected;
        if (investor!=null){
            try {
                boolean success = ConnectionToDB.StockWalletInDatabase(investor);
                if (success) {
                    System.out.println("Les données ont été sauvegardées avec succès.");
                } else {
                    System.out.println("Échec de la sauvegarde des données.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}