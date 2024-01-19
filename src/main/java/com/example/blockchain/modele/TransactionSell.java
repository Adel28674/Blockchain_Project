package com.example.blockchain.modele;

import java.io.IOException;

public class TransactionSell extends Transaction{
    public TransactionSell(Wallet originWallet, Value value) {
        super(originWallet, value);
    }

    public Boolean pay(){
        if (true){
            try {
                wallet.getOwner().sellValue(wallet,value); //provoque une exception
                payed = true;
                System.out.println("PAIEMENT ACCEPTé");
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("Error 404 -> La transaction est un échec.  ");
        return false;
    }
}
