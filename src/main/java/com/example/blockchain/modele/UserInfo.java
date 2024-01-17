package com.example.blockchain.modele;

import com.example.blockchain.modele.Wallet;

import java.util.List;

public class UserInfo {
    private String userLogin;

    private String userMDP;

    private String mail;

    private String userPhone;
    private String userName;

    public UserInfo(String userLogin, String userMDP, String mail, String userPhone, String userName) {
        this.userLogin = userLogin;
        this.userMDP = userMDP;
        this.mail = mail;
        this.userPhone = userPhone;
        this.userName = userName;
    }

    public UserInfo(){

    }


    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserMDP() {
        return userMDP;
    }

    public void setUserMDP(String userMDP) {
        this.userMDP = userMDP;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    @Override
    public String toString() {
        return "UserInfo{" +
                "userLogin='" + userLogin + '\'' +
                ", userMDP='" + userMDP + '\'' +
                ", mail='" + mail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
