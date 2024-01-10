package com.example.blockchain;

import com.example.blockchain.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static com.example.blockchain.AccueilPage.json;

public class HelloController {
    @FXML
    private Label Error_message;

    @FXML
    private Button btn_login;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    protected void onHelloButtonClick() throws SQLException, IOException {
        ConnectionToDB.connect();

        //tester inscription
        /*
        if (ConnectionToDB.signup("adel", "1234", "adel@gmail.com", "+33 7 89 56 98 21", "Adel")){
            System.out.println("Inscription valide");
        }else{
            System.out.println("Inscription Non Valide");
        }*/

        //tester connexion

        //user = adel ; mdp = 1234
        if (ConnectionToDB.verifyCredentials(login_field.getText(), password_field.getText())){
            System.out.println("Connecté");

            Stage stage = new Stage() ;
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueilPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

            Stage st = (Stage) btn_login.getScene().getWindow();
            st.close();


        }else{
            System.out.println("Non connecté");

            Error_message.setVisible(true);
        }


    }

    @FXML
    protected void noAccountonClick() throws SQLException, IOException {

        Stage stage = new Stage() ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InscriptionPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Stage st = (Stage) btn_login.getScene().getWindow();
        st.close();



    }
}