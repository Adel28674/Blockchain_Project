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


        // Requête d'insertion
        String insertionSQL = "INSERT INTO Users VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionToDb.con.prepareStatement(insertionSQL);
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "mihfezihzoieh");
            preparedStatement.setString(3, "123fezezfez4");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Exécution de la requête d'insertion
        int lignesModifiees = preparedStatement.executeUpdate();

        if (lignesModifiees > 0) {
            System.out.println("Insertion réussie !");
        } else {
            System.out.println("Échec de l'insertion !");
        }
    }
}