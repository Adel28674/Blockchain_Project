package com.example.blockchain.modele;

import java.io.IOException;
import java.io.Serializable;
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
    }

    public UUID createWalletAndReturnID() {
        Wallet newWallet = new Wallet(this);
        wallets.put(newWallet.getToken(), newWallet);
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

    public void cloneWallet(Wallet wallet) {
        Wallet wal = new Wallet(this);
        wal.setCapital(wallet.getCapital());
        wal.listValues = wallet.listValues;
        this.wallets.put(wal.getToken(), wal);
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
                    Double quantitySpend = cryptoCapital / price*quantity;
                    wallet.listValues.get(crypto.getSymbol()).setQuantity(Float.valueOf((float) (crypto.getQuantity()*quantitySpend)));
                } else {
                    Double quantitySpend = cryptoCapital / price*quantity;
                    wallet.listValues.put(value.getSymbol(), value);
                    wallet.listValues.get(crypto.getSymbol()).setQuantity(Float.valueOf((float) (crypto.getQuantity()*quantitySpend)));
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

        if (wallet.listValues.containsKey(value.getSymbol())) {
            Value val = wallet.listValues.get(value.getSymbol());
            if (val.getQuantity() >= value.getQuantity()) {
                val.setQuantity(val.getQuantity() - value.getQuantity());
                wallet.setCapital(wallet.getCapital() + (value.getPrice() * value.getQuantity()));
            }

        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nInvestor{}\n"

                ;
    }

    public static void main(String[] args) {
        List<String> info = ConnectionToDB.getUserInfo("a");

        Investor investor = new Investor(new UserInfo(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4)));

        investor.createWallet();


        for (Map.Entry mapentry : investor.wallets.entrySet()) {
            investor.addCapital((UUID) mapentry.getKey(), 7000000000.00);
            Wallet wallet = (Wallet) mapentry.getValue();
            System.out.println(wallet);
            Value value = new Cryptocurrency("BITCOIN", 100.00, "BITCOIN", 8);
            investor.buyValueWithCapital(wallet, value);

            Value value1 = new Stocking("APPLE", 100.00, "AAPL", 8, "apple");

            investor.buyValueWithCapital(wallet, value1);
            System.out.println(wallet.toString());
        }
        try {
            ConnectionToDB.StockWalletInDatabase(investor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            investor.setWallets(ConnectionToDB.getWalletsFromDatabase(investor));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        UUID uuid = investor.createWalletAndReturnID();
        investor.addCapital(uuid, 500.00);
        investor.cloneWallet(investor.wallets.get(uuid));
        System.out.println(investor.wallets.toString());

        try {
            ConnectionToDB.StockWalletInDatabase(investor);

            for (Map.Entry mapentry : investor.wallets.entrySet()) {
                Wallet wallet  = (Wallet) mapentry.getValue();
                System.out.println("Somme des prix du Wallet --------- " + wallet.getToken());
                System.out.println(wallet.getSumValues());
            }

            Object[] values = investor.wallets.keySet().toArray();
            for (int i = 0; i < values.length; i++) {
                investor.buyValueWithCapital(investor.wallets.get((UUID) values[i]), new Stocking("APPLE", 100.00, "AAPL", 2, "apple"));
                investor.buyValueWithCapital(investor.wallets.get((UUID) values[i]),new Cryptocurrency("BITCOIN", 100.00, "BITCOIN", 8));
                investor.buyValueWithCapital(investor.wallets.get((UUID) values[i]),new Cryptocurrency("ETHERUM", 100.00, "ETHERUM", 150));


            }

            for (Map.Entry mapentry : investor.wallets.entrySet()) {
                Wallet wallet  = (Wallet) mapentry.getValue();
                System.out.println("Deuxième Somme des prix du Wallet --------- " + wallet.getToken());
                System.out.println(wallet.getSumValues());
            }
            ConnectionToDB.StockWalletInDatabase(investor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
