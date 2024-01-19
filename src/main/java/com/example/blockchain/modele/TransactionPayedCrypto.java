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

    public Boolean pay(){
        if (true){
            wallet.getOwner().buyValueWithCrypto(wallet, cryptocurrencyToPay,value);
            payed = true;
            System.out.println("PAIEMENT ACCEPTé");
            return true;
        }
        System.out.println("Error 404 -> La transaction est un échec.  ");
        return false;
    }

    @Override
    public String toString() {
        return "TransactionPayedCrypto{" +
                "cryptocurrencyToPay=" + cryptocurrencyToPay +
                "} " + super.toString();
    }
}
