package com.example.blockchain.modele;

import java.io.IOException;

public class TransactionSell extends Transaction{
    public TransactionSell(Wallet originWallet, Value value) {
        super(originWallet, value);
    }

    public Boolean pay(){
        boolean b = false;
        try {
            b= wallet.getOwner().sellValue(wallet,value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (b){
            payed = true;
            System.out.println("PAIEMENT ACCEPTé");
            return true;
        }
        System.out.println("Error 404 -> La transaction est un échec.  ");
        return false;
    }
}
