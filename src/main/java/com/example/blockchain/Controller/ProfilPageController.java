package com.example.blockchain.Controller;

import com.example.blockchain.AccueilPage;
import com.example.blockchain.HelloApplication;
import com.example.blockchain.View.ProductElement;
import com.example.blockchain.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ProfilPageController {

    @FXML
    public Button btnSidebarAccueil;

    @FXML
    public Button btnSidebarWalletManager;

    @FXML
    public Button btnSidebaMarket;

    @FXML
    public Button btnSidebaProfile;


    @FXML
    public TextField username_field;
    @FXML
    public TextField name_field;
    @FXML
    public TextField mail_field;
    @FXML
    public TextField phone_field;
    @FXML
    public Button editProfile;
    @FXML
    public Button changePassword;

    private final String regexPhoneNumberPattern = "^\\d{10}$";
    private final String regexMailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


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

    private boolean isUsernameField_Valid() throws IOException, SQLException {
        String input = username_field.getText();

        if (input==null || input.matches("") || ConnectionToDB.existsUser(input)){
            return false;
        }
        return true;
    }

    private boolean isNameField_Valid() throws IOException {
        String input = name_field.getText();

        if (input==null || input.matches("")){
            return false;
        }
        return true;
    }

    private boolean isPasswdField_Valid() throws IOException {
        /*String input = passwd_field.getText();
        String input1 = confirm_passwd_field.getText();

        if (input==null || input.matches("") || !input.matches(input1)){
            return false;
        }

         */
        return true;
    }


    public boolean isMailField_Valid() throws IOException{
        return Pattern.compile(regexMailPattern)
                .matcher(mail_field.getText())
                .matches();
    }

    public boolean isPhoneField_Valid() throws IOException{
        return Pattern.compile(regexPhoneNumberPattern)
                .matcher(phone_field.getText())
                .matches();
    }

    public void btn_EditProfileClicked() throws IOException, SQLException {
        if (isNameField_Valid() && isMailField_Valid() && isPhoneField_Valid()
                && ConnectionToDB.UpdateInformation(CurrentUser.userConnected.getUserLogin(), name_field.getText(), mail_field.getText(), phone_field.getText())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vos informations ont été modifiés avec succès", ButtonType.OK);
            List<String> info = ConnectionToDB.getUserInfo(CurrentUser.userConnected.getUserLogin());
            CurrentUser.userConnected = new Investor(new UserInfo(info.get(0),info.get(1),info.get(2),info.get(3),info.get(4)));
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Err -> Quelque Chose ne va pas", ButtonType.OK);
            alert.showAndWait();
        }


    }



    public void btn_ChangePasswordClicked() {

    }

    public void setData(){
        name_field.setText(CurrentUser.userConnected.getUserName());
        mail_field.setText(CurrentUser.userConnected.getMail());
        phone_field.setText(CurrentUser.userConnected.getUserPhone());
    }
}
