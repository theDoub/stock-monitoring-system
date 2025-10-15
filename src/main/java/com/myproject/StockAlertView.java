package com.myproject;  

import java.util.HashMap;  
import java.util.Map;  

public class StockAlertView implements StockViewer {  
    private double alertThresholdHigh;    
    private double alertThresholdLow;    
    private Map<String, Double> lastAlertedPrices = new HashMap<>(); 

    public StockAlertView(double highThreshold, double lowThreshold) {  
        this.alertThresholdHigh = highThreshold;  
        this.alertThresholdLow = lowThreshold;  
    }  

    @Override  
    public void onUpdate(StockPrice stockPrice) {  
        String stockCode = stockPrice.getCode();  
        double currentPrice = stockPrice.getAvgPrice();  

      
        if (currentPrice >= alertThresholdHigh &&   
            (lastAlertedPrices.get(stockCode) == null || currentPrice > lastAlertedPrices.get(stockCode))) {  
            alertAbove(stockCode, currentPrice);  
            lastAlertedPrices.put(stockCode, currentPrice);  
        }  

       
        if (currentPrice <= alertThresholdLow &&   
            (lastAlertedPrices.get(stockCode) == null || currentPrice < lastAlertedPrices.get(stockCode))) {  
            alertBelow(stockCode, currentPrice);  
            lastAlertedPrices.put(stockCode, currentPrice);  
        }  
    }  

    private void alertAbove(String stockCode, double price) {  
        Logger.logAlert(stockCode, price); 
    }  

    private void alertBelow(String stockCode, double price) {  
        Logger.logAlert(stockCode, price);  
    }  
    public void registerAllStocks() {
        StockFeeder stockFeeder = StockFeeder.getInstance();
        for (Stock stock : stockFeeder.getStockList()) {
            stockFeeder.registerViewer(stock.getCode(), this);
        }
    }
}  