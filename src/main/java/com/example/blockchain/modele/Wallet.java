package com.example.blockchain.modele;

import java.util.UUID;

public class Wallet {

    private UserInfo owner;
    private UUID token = UUID.randomUUID();
    private int isepCoins;//changer par des titres


    public Wallet(UserInfo parOwner){
        this.owner = parOwner;
        this.isepCoins = 0;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public UUID getToken() {
        return token;
    }

    public int getIsepCoins() {
        return isepCoins;
    }

    public void setIsepCoins(int isepCoins) {
        this.isepCoins = isepCoins;
    }


}
