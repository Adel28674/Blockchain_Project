package com.example.blockchain.modele;

import java.util.ArrayList;

public class Blockchain {

    public static Bloc genesis = new Bloc(new ArrayList<>(), 0) ;
    public static ArrayList<Bloc> blocs = new ArrayList<>() ;

    public static Bloc getLastBloc(){
        return blocs.get(blocs.size()-1);
    }

    public static void addTransaction(Transaction transaction){
        Bloc b = getLastBloc();
        if (b.limitBlock()){
            b.processTransaction();
            ArrayList<Transaction> ar = new ArrayList<>();
            ar.add(transaction);
            Bloc newBlock = new Bloc(ar, b.getBlocHash());
            blocs.add(newBlock);
            System.out.println("AJOUT D'UN BLOC");
            return;
        }else {
            b.transactionArrayList.add(transaction);
            System.out.println("AJOUT d'une nouvelle TRANSAC");
            return;
        }
    }




}
