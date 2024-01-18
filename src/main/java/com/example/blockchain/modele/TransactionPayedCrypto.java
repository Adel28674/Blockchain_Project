package com.example.blockchain.modele;

public class TransactionPayedCrypto extends Transaction{

    public Cryptocurrency cryptocurrencyToPay ;

    public TransactionPayedCrypto(Wallet originWallet, Value value, Cryptocurrency parCrypto) {
        super(originWallet, value);
        cryptocurrencyToPay = parCrypto;

    }

    public Cryptocurrency getCryptocurrencyToPay() {
        return cryptocurrencyToPay;
    }

    public void setCryptocurrencyToPay(Cryptocurrency cryptocurrencyToPay) {
        this.cryptocurrencyToPay = cryptocurrencyToPay;
    }
}
