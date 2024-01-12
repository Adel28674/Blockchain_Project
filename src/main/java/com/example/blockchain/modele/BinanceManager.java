package com.example.blockchain.modele;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BinanceManager {

    public static String path = "https://api.binance.com/api/v3/";

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static JSONObject getOneCryptoValue(String symbol) throws IOException {
        String path1 = "ticker/price?symbol="+ Symbol.cryptoHM.get(symbol);
        JSONObject json  = new JSONObject();
        URL url = new URL(path+path1);

        BufferedReader in = null;

        StringBuilder jsontext = new StringBuilder();



        try{
            URLConnection yc = url.openConnection();
            System.out.println("Connected to Binance API");

            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()
                    )
            );
            String inputLine;
            while((inputLine = in.readLine()) != null){
                jsontext.append(inputLine);
                json = new JSONObject(jsontext.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            in.close();
        }


        return json;
    }

    public static JSONArray getCryptoValue(String symbol) throws IOException {
        //prend un intervale de 1 mois par défaut
        String path1 = "klines?symbol="+ Symbol.cryptoHM.get(symbol) + "&interval=1M";
        JSONArray json  = new JSONArray();
        URL url = new URL(path+path1);

        BufferedReader in = null;

        StringBuilder jsontext = new StringBuilder();



        try{
            URLConnection yc = url.openConnection();
            System.out.println("Connected to Binance API");

            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()
                    )
            );
            String inputLine;
            while((inputLine = in.readLine()) != null){
                jsontext.append(inputLine);
                json = new JSONArray(jsontext.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            in.close();
        }


        return json;
    }


    public static JSONArray getCryptoValueAt(String symbol, String interval) throws IOException {
        String path1 = "klines?symbol="+ Symbol.cryptoHM.get(symbol) + "&interval="+ interval;
        JSONArray json  = new JSONArray();
        URL url = new URL(path+path1);

        BufferedReader in = null;

        StringBuilder jsontext = new StringBuilder();



        try{
            URLConnection yc = url.openConnection();
            System.out.println("Connected to Binance API");

            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()
                    )
            );
            String inputLine;
            while((inputLine = in.readLine()) != null){
                jsontext.append(inputLine);
                json = new JSONArray(jsontext.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            in.close();
        }


        return json;
    }

    public static void startGetCryptoRealTime(String symbol){
        Runnable tache = new Runnable() {
            @Override
            public void run() {
                try {
                    Double bitcoinPrice = Double.parseDouble(getOneCryptoValue(symbol).getString("price"));

                    System.out.println("Prix actuel du Bitcoin : " + bitcoinPrice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        scheduler.scheduleAtFixedRate(tache, 0, 1, TimeUnit.SECONDS);
    }

    public static void stopAtCertainTime(int millisecondes){

        Timer timer = new Timer();
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                stopGetCryptoRealTime();
                timer.cancel();
                System.out.println("Timer exécuté !");
            }
        };

        timer.schedule(tache, 18000);

    }


    public static void stopGetCryptoRealTime() {
        scheduler.shutdown();
    }

    public static void main(String[] args) throws IOException {
        startGetCryptoRealTime("BITCOIN");
        stopAtCertainTime(5000);

    }
}
