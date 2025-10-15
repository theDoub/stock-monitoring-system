package com.myproject;

import java.util.HashMap;
import java.util.Map;

public class StockRealtimePriceView implements StockViewer {
    private final Map<String, Double> lastPrices = new HashMap<>();

    @Override
    public void onUpdate(StockPrice stockPrice) {
        if (stockPrice != null) {
            String code = stockPrice.getCode();
            double newPrice = stockPrice.getAvgPrice();

            if (lastPrices.containsKey(code)) {
                double oldPrice = lastPrices.get(code);
                if (Double.compare(oldPrice, newPrice) != 0) {
                    System.out.println("Price updated for stock " + code + ": " + oldPrice + " -> " + newPrice);
                }
            } else {
                System.out.println("New stock price added for " + code + ": " + newPrice);
            }

            // Update the last price
            lastPrices.put(code, newPrice);
        }
        
    }
    public void registerAllStocks() {
        StockFeeder stockFeeder = StockFeeder.getInstance();
        for (Stock stock : stockFeeder.getStockList()) {
            stockFeeder.registerViewer(stock.getCode(), this);
        }
    }
}