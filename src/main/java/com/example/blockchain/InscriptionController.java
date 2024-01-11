package com.example.blockchain;

import com.example.blockchain.modele.ConnectionToDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class InscriptionController implements Initializable {

    @FXML
    private TextField username_field;

    @FXML
    private TextField name_field;

    @FXML
    private TextField passwd_field;

    @FXML
    private TextField confirm_passwd_field;

    @FXML
    private TextField mail_field;

    @FXML
    private TextField phone_field;

    @FXML
    private Label usernameErrorMessage;

    @FXML
    private Label nameErrorMessage;

    @FXML
    private Label passwdErrorMessage;


    @FXML
    private Label mailErrorMessage;

    @FXML
    private Label phoneErrorMessage;

    private final String regexPhoneNumberPattern = "^\\d{10}$";
    private final String regexMailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private boolean usernameField_Validity = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        username_field.setOnKeyTyped(event->{
            try {
                if (username_field.getText().matches("")){
                    //pass
                    //si le champ est vide on ne fait rien
                } else if (isUsernameField_Valid()) {
                    username_field.setStyle("-fx-border-color: #0ac40d; -fx-focus-color: #0ac40d;");
                    usernameErrorMessage.setVisible(false);
                }
                else{
                    username_field.setStyle("-fx-border-color: #cf2317; -fx-focus-color: #cf2317;");
                    usernameErrorMessage.setVisible(true);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        name_field.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                try {
                    if (name_field.getText().matches("")){
                        //pass
                        //si le champ est vide on ne fait rien
                    } else if (isNameField_Valid()) {
                        name_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                        nameErrorMessage.setVisible(false);
                    }else{
                        name_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #cf2317;");
                        nameErrorMessage.setVisible(true);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });



        /*
        confirm_passwd_field.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                try {
                    if (isPasswdField_Valid()) {
                        passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                        confirm_passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                        passwdErrorMessage.setVisible(false);
                    }else{
                        passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                        confirm_passwd_field.setStyle("-fx-text-box-border: #cf2317; -fx-focus-color: #cf2317;");
                        passwdErrorMessage.setVisible(true);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });*/

        confirm_passwd_field.setOnKeyTyped(event->{
            try {
                if (isPasswdField_Valid()) {
                    passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    confirm_passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    passwdErrorMessage.setVisible(false);
                }else{
                    passwd_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    confirm_passwd_field.setStyle("-fx-text-box-border: #cf2317; -fx-focus-color: #cf2317;");
                    passwdErrorMessage.setVisible(true);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        mail_field.setOnKeyTyped(event->{
            try {
                if (mail_field.getText().matches("")){
                    //pass
                    //si le champ est vide on ne fait rien
                } else if (isMailField_Valid()) {


                    mail_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    mailErrorMessage.setVisible(false);
                }else{
                    mail_field.setStyle("-fx-text-box-border: #cf2317; -fx-focus-color: #cf2317;");
                    mailErrorMessage.setVisible(true);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                });


        phone_field.setOnKeyTyped(event->{
            try {
                if (phone_field.getText().matches("")) {
                    //pass
                    //si le champ est vide on ne fait rien
                } else if (isPhoneField_Valid()) {
                    phone_field.setStyle("-fx-text-box-border: #0ac40d; -fx-focus-color: #0ac40d;");
                    phoneErrorMessage.setVisible(false);
                } else {
                    phone_field.setStyle("-fx-text-box-border: #cf2317; -fx-focus-color: #cf2317;");
                    phoneErrorMessage.setVisible(true);
                }

            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



    private boolean isUsernameField_Valid() throws IOException, SQLException {
        String input = username_field.getText();

        if (input==null || input.matches("") || ConnectionToDB.exists(input)){
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
        String input = passwd_field.getText();
        String input1 = confirm_passwd_field.getText();

        if (input==null || input.matches("") || !input.matches(input1)){
            return false;
        }
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

    public void btn_RegistrationClicked(ActionEvent actionEvent) throws SQLException, IOException, InterruptedException {

        if (isUsernameField_Valid() && isNameField_Valid() && isPasswdField_Valid() && isMailField_Valid() && isPhoneField_Valid()
        && ConnectionToDB.signup(username_field.getText(), passwd_field.getText(), mail_field.getText(), phone_field.getText(), name_field.getText())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vous avez été inscrit avec succès", ButtonType.OK);
            alert.showAndWait();
            Stage stage = new Stage() ;
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueilPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

            Stage st = (Stage) username_field.getScene().getWindow();
            st.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite\nVérifiez que vous avez bien remplis tout les champs\nVérifiez également de respecter les messages d'erreurs", ButtonType.OK);
            alert.showAndWait();
        }
    }


}
