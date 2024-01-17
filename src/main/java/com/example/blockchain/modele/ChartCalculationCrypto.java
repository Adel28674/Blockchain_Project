package com.example.blockchain.modele;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartCalculationCrypto extends ApplicationFrame {

    public ChartCalculationCrypto(String title, String symbol) throws IOException {
        super(title);

        final DefaultHighLowDataset dataset = createDataset(BinanceManager.getCryptoValue(symbol));
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 700));
        setContentPane(chartPanel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                if (event.getWindow().equals(this)) {
                    dispose();
                }
            }
        });
    }

    @Override
    public void windowClosing(WindowEvent event){
        if (event.getWindow() == this) {
            this.dispose();
        }
    }



    private DefaultHighLowDataset createDataset(JSONArray json) {


        Date[] date = new Date[json.length()];
        double[] high = new double[json.length()];
        double[] low = new double[json.length()];
        double[] open = new double[json.length()];
        double[] close = new double[json.length()];
        double[] volume = new double[json.length()];


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; (i < json.length()) || (i<20); i++) {
            JSONArray ligne  = json.getJSONArray(i);
            try {

                date[i] = new Date(ligne.getLong(0));
                open[i] = ligne.getDouble(1);
                high[i] = ligne.getDouble(3);
                low[i] = ligne.getDouble(2);
                close[i] = ligne.getDouble(4);
                volume[i] = ligne.getDouble(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return new DefaultHighLowDataset("Sample", date, high, low, open, close, volume);
    }



    private JFreeChart createChart(final DefaultHighLowDataset dataset) {
        JFreeChart chart = ChartFactory.createCandlestickChart(
                "Candlestick Chart",
                "Date",
                "Price",
                dataset,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        CandlestickRenderer renderer = new CandlestickRenderer();
        renderer.setCandleWidth(10.0);
        plot.setRenderer(renderer);
        return chart;
    }



    public void afficher(){
        this.pack();
        this.setVisible(true);
    }


    public static void main(String[] args) {
        ChartCalculationCrypto chart = null;
        try {
            chart = new ChartCalculationCrypto("Candle Stick Chart","ETHERUM");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        chart.afficher();

    }
}
