package com.example.blockchain;

import com.example.blockchain.modele.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        ConnectionToDB.connect();

        //tester inscription
        /*
        if (ConnectionToDB.signup("adel", "1234", "adel@gmail.com", "+33 7 89 56 98 21", "Adel")){
            System.out.println("Inscription valide");
        }else{
            System.out.println("Inscription Non Valide");
        }*/

        //tester connexion

        if (ConnectionToDB.verifyCredentials("adel", "1234")){
            System.out.println("Connecté");
        }else{
            System.out.println("Non connecté");
        }

    }
}