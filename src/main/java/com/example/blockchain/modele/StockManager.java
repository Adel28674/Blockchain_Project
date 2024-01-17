package com.example.blockchain.modele;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputFilter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StockManager {
    public static void main(String[] args) throws IOException {
        /*
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=GOOG&output=compact&apikey=RUCNTNS1KKTTBZUV");

        try {
            HttpResponse response = httpClient.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        JSONObject json= getStockPricesMonthly("GOOG");
        System.out.println(json.toString());
    }

    public static JSONObject getStockPricesMonthly(String symbol){
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol="+ symbol + "&apikey=GRFMXBT58ZTRW3MT");
        JSONObject json ;
        StringBuilder jsontext = new StringBuilder();
        try {
            HttpResponse response = httpClient.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String inputLine;
            while((inputLine = reader.readLine()) != null){
                jsontext.append(inputLine);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    /*
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
        HashMap<String, Object> root = mapper.readValue(response.toString(), typeRef);
        HashMap<String, HashMap<String, String>> timeSeries = (HashMap<String, HashMap<String, String>>) root.get("Time Series (Daily)");

        TreeMap<String, HashMap<String, String>> sortedData = new TreeMap<>(Collections.reverseOrder());
        sortedData.putAll(timeSeries);
*/

        json = new JSONObject(jsontext.toString());

        return json;
    }



}
