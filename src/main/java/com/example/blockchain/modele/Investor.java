package com.example.blockchain.modele;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Investor extends UserInfo implements Serializable {

    private HashMap<UUID, Wallet> wallets;

    public Investor(String userLogin, String userMDP, String mail, String userPhone, String userName) {
        super(userLogin, userMDP, mail, userPhone, userName);
        wallets = new HashMap<>();
    }

    public Investor(UserInfo userInfo) {
        super(userInfo.getUserLogin(), userInfo.getUserMDP(), userInfo.getMail(), userInfo.getUserPhone(), userInfo.getUserName());
        wallets = new HashMap<>();
    }

    public Investor(){
        super();
        this.wallets = new HashMap<>();
    }


    public void createWallet() {
        Wallet newWallet = new Wallet(this);
        wallets.put(newWallet.getToken(), newWallet);
        try {
            ConnectionToDB.StockWalletInDatabase(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeWallet(UUID id) {
        wallets.remove(id);
        try {
            ConnectionToDB.StockWalletInDatabase(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID createWalletAndReturnID() {
        Wallet newWallet = new Wallet(this);
        wallets.put(newWallet.getToken(), newWallet);
        try {
            ConnectionToDB.StockWalletInDatabase(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newWallet.getToken();
    }


    public HashMap<UUID, Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(HashMap<UUID, Wallet> wallets) {
        this.wallets = wallets;
    }

    public void addCapital(UUID parIdWallet, Double parCapital) {
        wallets.get(parIdWallet).addCapital(parCapital);
    }

    public void cloneWallet(UUID uuid) {
        Wallet walletCloning = wallets.get(uuid);
        UUID id = createWalletAndReturnID();
        Wallet walletCloned = wallets.get(id);
        walletCloned.setCapital(walletCloning.getCapital());
        walletCloned.listValues = walletCloning.listValues;
        this.wallets.put(walletCloned.getToken(), walletCloned);
    }

    public void buyValueWithCapital(Wallet wallet, Value value) {
        try{
            Double price = value.getPrice();
            System.out.println("TEST DE VALIDIT2 DE LA TRANSAC" + (wallet.getCapital() >= (price * value.getQuantity())));
            if (wallet.getCapital() >= (price * value.getQuantity())) {
                if (wallet.listValues.containsKey(value.getSymbol())) {
                    Value val = wallet.listValues.get(value.getSymbol());
                    val.setQuantity(val.getQuantity() + value.getQuantity());
                    wallet.setCapital(wallet.getCapital() - (price * value.getQuantity()));
                } else {
                    wallet.listValues.put(value.getSymbol(), value);
                    wallet.setCapital(wallet.getCapital() - (price * value.getQuantity()));
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pas assez de capital", ButtonType.CLOSE);
                alert.showAndWait();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void buyValueWithCrypto(Wallet wallet, Cryptocurrency crypto, Value value) {
        try{
            Double price = value.getPrice();
            float quantity = value.getQuantity();
            Double cryptoCapital = crypto.getPrice()* crypto.getQuantity();
            if (cryptoCapital > price * quantity) {
                if (wallet.listValues.containsKey(value.getSymbol())) {
                    Value val = wallet.listValues.get(value.getSymbol());
                    val.setQuantity(val.getQuantity() + value.getQuantity());
                    Double quantitySpend = price * quantity / crypto.getPrice();
                    crypto.setQuantity((float) (crypto.getQuantity() - quantitySpend));
                    wallet.listValues.put(crypto.getSymbol(), crypto);

                } else {
                    Double quantitySpend = price * quantity / crypto.getPrice();
                    wallet.listValues.put(value.getSymbol(), value);
                    crypto.setQuantity((float) (crypto.getQuantity() - quantitySpend));
                    wallet.listValues.put(crypto.getSymbol(), crypto);
                }
            } else if (cryptoCapital == price * quantity) {
                crypto.setQuantity(0);
                wallet.listValues.put(crypto.getSymbol(), crypto);
                Value val = wallet.listValues.get(value.getSymbol());
                val.setQuantity(val.getQuantity() + value.getQuantity());
                wallet.listValues.put(val.getSymbol(), val);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void sellValue(Wallet wallet, Value value) throws IOException {

        if (wallet.listValues.containsKey(value.getSymbol()) && (!(value.getQuantity() ==0))) {
            Value val = wallet.listValues.get(value.getSymbol());
            if (val.getQuantity() >= value.getQuantity()) {
                wallet.setCapital(wallet.getCapital() + (value.getPrice() * value.getQuantity()));
                val.setQuantity(val.getQuantity() - value.getQuantity());

            }

        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nInvestor{}\n"

                ;
    }

}
