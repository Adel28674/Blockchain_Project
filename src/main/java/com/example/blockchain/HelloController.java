package com.example.blockchain;

import com.example.blockchain.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.blockchain.modele.ConnectionToDB.getWalletsFromDatabase;

public class HelloController {
    @FXML
    private Label Error_message;

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_signup;

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
            List<String> info = ConnectionToDB.getUserInfo(login_field.getText());
            CurrentUser.userConnected = new Investor(new UserInfo(info.get(0),info.get(1),info.get(2),info.get(3),info.get(4)));
            try {
                CurrentUser.userConnected.setWallets(getWalletsFromDatabase(CurrentUser.userConnected));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Connecté");
            System.out.println(CurrentUser.userConnected.getWallets().toString());
            Stage stage = new Stage() ;
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueilPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
            AccueilPage accueilPage = fxmlLoader.getController();
            accueilPage.getClass().getResource("/com/example/blockchain/css/accueilcss.css").toExternalForm();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            accueilPage.setData();

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
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Stage st = (Stage) btn_login.getScene().getWindow();
        st.close();



    }
}