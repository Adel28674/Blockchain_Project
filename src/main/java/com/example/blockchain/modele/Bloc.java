package com.example.blockchain.modele;

import java.util.LinkedList;

public class Bloc {
    public final LinkedList<Transaction> transactionsQueue;

    public Bloc(){
        this.transactionsQueue = new LinkedList<Transaction>();
    }

    public Bloc add(Transaction par){
        if (transactionsQueue.size()<10){
            transactionsQueue.add(par);
            System.out.println("AJOUT d'une nouvelle TRANSAC");
            return this;
        } else if (transactionsQueue.size()==10) {
            System.out.println("AJOUT d'un nouveau BLOC");
            processTransaction();
            Bloc b = new Bloc();
            b.add(par);
            return b;
        }

        return new Bloc();
    }

    @Override
    public String toString() {
        String aff = "";
        for (int i = 0; i < transactionsQueue.size(); i++) {
            aff += transactionsQueue.get(i).toString() + "\n";
        }
        return aff;
    }

    public void processTransaction(){
        int i = 0;
        System.out.println("PROCESSING ################################################");
        while(i < transactionsQueue.size()){
            transactionsQueue.get(i).pay();
            i++;
        }
    }



}
