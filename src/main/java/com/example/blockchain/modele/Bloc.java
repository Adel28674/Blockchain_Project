package com.example.blockchain.modele;

import java.util.ArrayList;
import java.util.Arrays;

public class Bloc {
    public ArrayList<Transaction> transactionArrayList;

    private int previousHash;
    private int blocHash;

    public Bloc(ArrayList<Transaction> transactions, int parPreviousHash){
        this.transactionArrayList = transactions;
        this.previousHash = parPreviousHash;
        Object[] contens = {Arrays.hashCode(transactionArrayList.toArray()), previousHash};

        blocHash = Arrays.hashCode(contens);
    }

    public Bloc add(Transaction par){
        if (transactionArrayList.size()<10){
            transactionArrayList.add(par);
            System.out.println("AJOUT d'une nouvelle TRANSAC");
            return this;
        } else if (transactionArrayList.size()==10) {
            System.out.println("AJOUT d'un nouveau BLOC");
            processTransaction();
            ArrayList<Transaction> ar = new ArrayList<>();
            ar.add(par);
            Bloc b = new Bloc(ar, this.getBlocHash());
            return b;
        }

        return new Bloc(new ArrayList<>(), this.getBlocHash());
    }

    @Override
    public String toString() {
        String aff = "";
        for (int i = 0; i < transactionArrayList.size(); i++) {
            aff += transactionArrayList.get(i).toString() + "\n";
        }
        return aff;
    }

    public void processTransaction(){
        int i = 0;
        System.out.println("PROCESSING ################################################");
        while(i < transactionArrayList.size()){
            transactionArrayList.get(i).pay();
            i++;
        }
    }

    public ArrayList<Transaction> getTransactionArrayList() {
        return transactionArrayList;
    }

    public void setTransactionArrayList(ArrayList<Transaction> transactionArrayList) {
        this.transactionArrayList = transactionArrayList;
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(int previousHash) {
        this.previousHash = previousHash;
    }

    public int getBlocHash() {
        return blocHash;
    }

    public void setBlocHash(int blocHash) {
        this.blocHash = blocHash;
    }
}
