package com.example.blockchain.modele;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.timeseries.TimeSeries;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;



import java.io.IOException;
import java.io.ObjectInputFilter;
import java.math.BigDecimal;

import static com.crazzyghost.alphavantage.AlphaVantage.*;

public class StockManager {
    public static void main(String[] args) throws IOException {

        // Remplacez 'YOUR_API_KEY' par votre propre clé API Alpha Vantage
        String apiKey = "RUCNTNS1KKTTBZUV";

        // Configurez Alpha Vantage avec votre clé API
        Config config = Config.builder().key(apiKey).build();
        api().init(config);

        try {
            // Récupérez les données de série temporelle pour les actions de Google


            TimeSeriesResponse timeSeriesResponse = AlphaVantage.api()
                    .timeSeries()
                    .intraday()
                    .fetchSync();

            System.out.println(timeSeriesResponse.toString());


        } catch (AlphaVantageException e) {
            e.printStackTrace();
        }
    }

}
