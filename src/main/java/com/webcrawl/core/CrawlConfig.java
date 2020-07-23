package com.webcrawl.core;

public class CrawlConfig {
    String url = "";
    String stockTicker = "";
    String driverType = "";
    String chromeDriverLocation = "";

    public String getChromeDriverLocation() {
        return chromeDriverLocation;
    }

    public void setChromeDriverLocation(String chromeDriverLocation) {
        this.chromeDriverLocation = chromeDriverLocation;
    }

    public String getFirefoxDriverLocation() {
        return firefoxDriverLocation;
    }

    public void setFirefoxDriverLocation(String firefoxDriverLocation) {
        this.firefoxDriverLocation = firefoxDriverLocation;
    }

    String firefoxDriverLocation = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }
}
