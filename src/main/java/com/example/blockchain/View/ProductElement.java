package com.example.blockchain.View;

import com.example.blockchain.modele.CurrentUser;
import com.example.blockchain.modele.Value;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

import java.io.IOException;

public class ProductElement extends VBox {
    public ProductElement(Value value){
        Label nomValeur = new Label();
        nomValeur.setText(value.getName());

        Label prixUnitaire = new Label();
        try {
            prixUnitaire.setText(value.getPrice().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Button buyBtn  = new Button("Buy");

        buyBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //CurrentUser.userConnected.buyValueWithCapital();
            }
        });
    }

}
