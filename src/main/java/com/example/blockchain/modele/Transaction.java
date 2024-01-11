package com.example.blockchain.modele;


public class Transaction {
    protected final Wallet originWallet;
    protected final Wallet destinationWallet;
    protected int isepCoins;
    protected Boolean payed;

    public Transaction(Wallet parOriginWallet, Wallet parDestinationWallet, int parIsepCoins){
        this.originWallet = parOriginWallet;
        this.destinationWallet = parDestinationWallet;
        this.isepCoins = parIsepCoins;
        this.payed = false;
    }

    public Wallet getOriginWallet() {
        return originWallet;
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
        if (this.isValidTransaction()){
            this.getOriginWallet().setIsepCoins(this.getOriginWallet().getIsepCoins()-this.getIsepCoins());
            this.getDestinationWallet().setIsepCoins(this.getDestinationWallet().getIsepCoins()+this.getIsepCoins());
            payed = true;
            //this.toString();
            System.out.println("PAIEMENT ACCEPTé");
            return true;
        }
        System.out.println("Error 404 -> La transaction est un échec.  ");
        return false;
    }

    public boolean isValidTransaction(){
        return ((this.getOriginWallet().getIsepCoins() - this.getIsepCoins() >= 0) && !(this.getOriginWallet().getToken().equals(this.getDestinationWallet().getToken())));

    }

    @Override
    public String toString() {
        return "Transaction{" +
                originWallet.getOwner() + " fait une transaction de " + isepCoins + " avec " + destinationWallet.getOwner()+
                '}';
    }
}
