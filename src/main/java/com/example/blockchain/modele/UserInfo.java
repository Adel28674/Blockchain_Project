package com.example.blockchain.modele;

import com.example.blockchain.modele.Wallet;

import java.util.List;

public class UserInfo {
    private List<Wallet> walletList;

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

    public UserInfo(String parLogin, String parPasswd, String parMail){
        this.userLogin = parLogin;
        this.userMDP = parPasswd;
        this.mail = parMail;
    }

    public List<Wallet> getWalletList() {
        return walletList;
    }

    public void addWalletList(Wallet wallet) {
        this.getWalletList().add(wallet);
    }

    public void deleteWalletList(Wallet wallet) {
        if (this.getWalletList().contains(wallet)){
            this.getWalletList().remove(wallet);
        }else{
            System.out.println("Ce portefeuille n'existe pas");
        }
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
