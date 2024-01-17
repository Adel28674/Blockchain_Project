package com.example.blockchain.modele;


import java.io.IOException;

public class Transaction {
    protected final Wallet wallet;
    protected final Wallet destinationWallet;

    protected long timestamp;

    protected Value value;
    protected int isepCoins;
    protected Boolean payed;

    public Transaction(Wallet originWallet, Wallet destinationWallet, Value value, int isepCoins) {
        this.wallet = originWallet;
        this.destinationWallet = destinationWallet;
        this.timestamp = System.currentTimeMillis();
        this.value = value;
        this.isepCoins = isepCoins;
        this.payed = false;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Wallet getDestinationWallet() {
        return destinationWallet;
    }

    public int getIsepCoins() {
        return isepCoins;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean par) {
        this.payed = par;
    }

    public Boolean pay(){
        if (true){
            this.getWallet().addCapital(this.getWallet().getCapital()-this.getIsepCoins());
            this.getDestinationWallet().addCapital(this.getDestinationWallet().getCapital()+this.getIsepCoins());
            payed = true;
            //this.toString();
            System.out.println("PAIEMENT ACCEPTé");
            return true;
        }
        System.out.println("Error 404 -> La transaction est un échec.  ");
        return false;
    }

    public Boolean payWithCapital() throws IOException {
        if (this.isValidTransactionWithCapital()){
            wallet.getOwner().buyValueWithCapital(wallet, value);
            payed = true;
            System.out.println("PAIEMENT ACCEPTé");
            return true;
        }
        System.out.println("Error 404 -> La transaction est un échec.  ");
        return false;
    }

    public boolean isValidTransactionWithCapital() throws IOException {
        if (wallet.getCapital()>= (value.getPrice()* value.getQuantity())){
            return true;
        }
        return  false;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                wallet.getOwner() + " fait une transaction de " + isepCoins + " avec " + destinationWallet.getOwner()+
                '}';
    }
}
