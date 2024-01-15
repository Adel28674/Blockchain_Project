package com.example.blockchain.modele;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class ChartCalculationStock extends ApplicationFrame {

    public ChartCalculationStock(String title, JSONObject json) {
        super(title);

        final DefaultHighLowDataset dataset = createDataset(json);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 700));
        setContentPane(chartPanel);

    }

    private DefaultHighLowDataset createDataset(JSONObject json) {
        JSONObject timeSeries = json.getJSONObject("Monthly Time Series");

        Date[] date = new Date[timeSeries.length()];
        double[] high = new double[timeSeries.length()];
        double[] low = new double[timeSeries.length()];
        double[] open = new double[timeSeries.length()];
        double[] close = new double[timeSeries.length()];
        double[] volume = new double[timeSeries.length()];


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int index = 0;
        for (String dateString : timeSeries.keySet()) {
            JSONObject dayData = timeSeries.getJSONObject(dateString);

            try {
                date[index] = dateFormat.parse(dateString);
                open[index] = dayData.getDouble("1. open");
                high[index] = dayData.getDouble("2. high");
                low[index] = dayData.getDouble("3. low");
                close[index] = dayData.getDouble("4. close");
                volume[index] = dayData.getDouble("5. volume");
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
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


    public static LineChart<Number, Number> graphique(){
        JSONObject json = StockManager.getStockPricesMonthly("IBM");

        JSONObject timeSeries = json.getJSONObject("Monthly Time Series");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        Date[] date = new Date[timeSeries.length()];
        double[] high = new double[timeSeries.length()];
        double[] low = new double[timeSeries.length()];
        double[] open = new double[timeSeries.length()];
        double[] close = new double[timeSeries.length()];
        double[] volume = new double[timeSeries.length()];


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int index = 0;
        for (String dateString : timeSeries.keySet()) {
            JSONObject dayData = timeSeries.getJSONObject(dateString);

            try {
                date[index] = dateFormat.parse(dateString);
                open[index] = dayData.getDouble("1. open");
                high[index] = dayData.getDouble("2. high");
                low[index] = dayData.getDouble("3. low");
                close[index] = dayData.getDouble("4. close");
                volume[index] = dayData.getDouble("5. volume");
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
        ArrayList<Double> closingPrices = new ArrayList<Double>();

        for (String d : timeSeries.keySet()) {
            double closePrice = timeSeries.getJSONObject(d).getDouble("4. close");
            closingPrices.add(closePrice);
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("IBM");
        Collections.reverse(closingPrices);

        for (int i = 0; i < closingPrices.size(); i++) {
            series.getData().add(new XYChart.Data<>(i - 1, closingPrices.get(i)));
        }
        return lineChart;
    }

    public static void main(String[] args) {
        /*ChartCalculationStock chart = new ChartCalculationStock("Candle Stick Chart", StockManager.getStockPricesMonthly("IBM"));
        chart.pack();
        chart.setVisible(true);
*/
        graphique();
    }

}