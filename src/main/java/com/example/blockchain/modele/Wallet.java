package com.example.blockchain.modele;

import java.io.*;
import java.security.cert.CertificateNotYetValidException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Wallet implements Serializable{

    private Investor owner;

    private String name;
    private UUID token = UUID.randomUUID();
    private Double capital;//changer par des titres

    public HashMap<String, Value> listValues ;


    public Wallet(Investor parOwner){
        this.owner = parOwner;
        this.capital = 00.00;
        this.listValues = new HashMap<>();
    }

    public Wallet(Investor parOwner, String parName){
        this.owner = parOwner;
        this.capital = 00.00;
        this.listValues = new HashMap<>();
        this.name = parName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Investor getOwner() {
        return owner;
    }

    public UUID getToken() {
        return token;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public void addCapital(Double capital) {
        this.capital = this.capital + capital;
    }


    @Override
    public String toString() {
        return "Wallet{" +
                "owner=" + owner +
                ", token=" + token +
                ", capital=" + capital +
                ", listValues=" + listValues +
                '}';
    }

    public Double getSumValues() throws IOException {
        Double sum  = this.getCapital();

        for (Map.Entry mapentry : listValues.entrySet()) {
            Value val = (Value) mapentry.getValue();
            sum += (val.getPrice()*val.getQuantity());
        }

        return sum;
    }

    public double getPartCrypto() throws IOException {
        Double sumCrypto  = 0.0;

        for (Map.Entry mapentry : listValues.entrySet()) {
            Value val = (Value) mapentry.getValue();

            if (val instanceof Cryptocurrency){
                sumCrypto += (val.getPrice()*val.getQuantity());
            }
        }

        return sumCrypto;

    }

    public double getPartStock() throws IOException {
        Double sumStock  = 0.0;

        for (Map.Entry mapentry : listValues.entrySet()) {
            Value val = (Value) mapentry.getValue();

            if (val instanceof Stocking){
                sumStock += (val.getPrice()*val.getQuantity());
            }
        }

        return sumStock;

    }

    public static byte[] serializeListValues(HashMap<UUID, Wallet> walletHashMap) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(walletHashMap);
            return baos.toByteArray();
        }
    }

    public static HashMap<UUID, Wallet> deserializeWalletsList(byte[] data)  {
        if (data!=null){
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return (HashMap<UUID, Wallet>) ois.readObject();
            }catch (IOException e) {
                e.printStackTrace();
                return new HashMap<>();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return new HashMap<>();
            }
        }
        return new HashMap<>();
    }
}
