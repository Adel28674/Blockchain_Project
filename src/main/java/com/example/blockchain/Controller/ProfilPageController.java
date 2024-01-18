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
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
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
    public Label name;
    @FXML
    public Label mail;
    @FXML
    public Label phone;
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
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/blockchain/css/walletboxcss.css").toExternalForm());
        WalletManagerController walCon = fxmlLoader.getController();
        walCon.setData();

        stage.setTitle("Wallet Manager!");
        stage.setScene(scene);
        stage.show();
    }

    public void onbtnSidebaMarketClicked() throws IOException {


        Stage stage1 = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("market.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        MarketController m = fxmlLoader.getController();
        m.setData();

        stage1.setTitle("Market!");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) btnSidebarAccueil.getScene().getWindow();
        stage.close();
    }

    public void onbtnProfileClicked() throws IOException {

        Stage stage1 = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profil-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        ProfilPageController p = fxmlLoader.getController();
        p.setData();

        stage1.setTitle("Votre Profil!");
        stage1.setScene(scene);
        stage1.show();
        Stage stage = (Stage) btnSidebarAccueil.getScene().getWindow();
        stage.close();
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change Password");
        alert.setHeaderText("Insérer un mot de passe valide");

        VBox v= new VBox();

        TextField passwd_field = new TextField();
        passwd_field.setPromptText("Mot de passe");

        Label passwdErrorMessage = new Label("Les deux mots de passe ne correspondent pas");
        passwdErrorMessage.setVisible(false);


        TextField confirm_passwd_field = new TextField();
        confirm_passwd_field.setPromptText("Confirmez");


        v.getChildren().addAll(passwd_field,confirm_passwd_field,passwdErrorMessage);


        confirm_passwd_field.setOnKeyTyped(event->{
            try {
                String input = passwd_field.getText();
                String input1 = confirm_passwd_field.getText();
                if (input==null || input.matches("") || !input.matches(input1)) {
                    passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    confirm_passwd_field.setStyle("-fx-text-box-border: #cf2317; -fx-focus-color: #cf2317;");
                    passwdErrorMessage.setVisible(true);
                }else{
                    passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    confirm_passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    passwdErrorMessage.setVisible(false);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        alert.getDialogPane().setContent(v);


        alert.showAndWait().ifPresent(response -> {
            String input = passwd_field.getText();
            String input1 = confirm_passwd_field.getText();
            if (response == ButtonType.OK && input==null || input.matches("") || input.matches(input1)) {
                try {
                    ConnectionToDB.changePassword(CurrentUser.userConnected.getUserLogin(), input);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                alert.close();
            }
            if (response == ButtonType.CANCEL){
                alert.close();
            }
        });

    }

    public void setData(){
        name_field.setText(CurrentUser.userConnected.getUserName());
        mail_field.setText(CurrentUser.userConnected.getMail());
        phone_field.setText(CurrentUser.userConnected.getUserPhone());

        name.setText(CurrentUser.userConnected.getUserName());
        mail.setText(CurrentUser.userConnected.getMail());
        phone.setText(CurrentUser.userConnected.getUserPhone());


    }
}
