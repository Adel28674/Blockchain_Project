package com.example.blockchain;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        ConnectionToDb.connect();

        //tester inscription
        /*
        if (ConnectionToDb.signup("adel", "1234", "adel@gmail.com", "+33 7 89 56 98 21", "Adel")){
            System.out.println("Inscription valide");
        }else{
            System.out.println("Inscription Non Valide");
        }*/

        //tester connexion

        if (ConnectionToDb.verifyCredentials("adel", "1234")){
            System.out.println("Connecté");
        }else{
            System.out.println("Non connecté");
        }

    }
}