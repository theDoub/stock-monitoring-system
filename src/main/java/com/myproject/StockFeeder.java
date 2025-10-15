package com.myproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockFeeder {
    private List<Stock> stockList = new ArrayList<>();
    private Map<String, List<StockViewer>> viewers = new HashMap<>();
    private static StockFeeder instance = null;
    
    private StockFeeder() {}

    public static StockFeeder getInstance() {
        if (instance == null) {
            synchronized (StockFeeder.class) {
                if (instance == null) {
                    instance = new StockFeeder();
                }
            }
        }
        return instance;
    }

    public void addStock(Stock stock) {
        if (stock != null && !stockList.contains(stock)) {
            stockList.add(stock);
            viewers.putIfAbsent(stock.getCode(), new ArrayList<>());
        }
    }

    public void registerViewer(String code, StockViewer stockViewer) {
        if (viewers.containsKey(code)) {
            List<StockViewer> stockViewers = viewers.get(code);
            if (!stockViewers.contains(stockViewer)) {
                stockViewers.add(stockViewer);
            }
        } else {
            System.err.println("Stock with code " + code + " does not exist.");
        }
    }

    public void unregisterViewer(String code, StockViewer stockViewer) {
        if (viewers.containsKey(code)) {
            List<StockViewer> stockViewers = viewers.get(code);
            if (stockViewers.remove(stockViewer)) {
                System.out.println("Viewer unregistered successfully.");
            } else {
                System.err.println("Viewer not found for stock code " + code + ".");
            }
        } else {
            System.err.println("Stock with code " + code + " does not exist.");
        }
    }

    public void notify(StockPrice stockPrice) {
        if (stockPrice != null) {
            String code = stockPrice.getCode();
            if (viewers.containsKey(code)) {
                for (StockViewer viewer : viewers.get(code)) {
                    viewer.onUpdate(stockPrice);
                }
            } else {
                System.err.println("No viewers registered for stock code " + code + ".");
            }
        }
    }
    public void addStockIfAbsent(String code, String name) {
        
        if (stockList.stream().noneMatch(stock -> stock.getCode().equals(code))) {
            Stock stock = new Stock(code, name);
            stockList.add(stock);
            viewers.putIfAbsent(code, new ArrayList<>());
        }
    }
    public List<Stock> getStockList() {
        return new ArrayList<>(stockList); 
    }
    
}