package com.webcrawl.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CrawlConfigBuilder {

    InputStream inputStream;
    Properties props;
    CrawlConfig crawlConfig;

    public CrawlConfig getCrawlConfig() throws IOException {
        try {
            crawlConfig = new CrawlConfig();
            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                props.load(inputStream);
            }
            else {
                throw new FileNotFoundException("property file : " + propFileName);
            }
            crawlConfig.setUrl(props.getProperty("url"));
            crawlConfig.setDriverType(props.getProperty("driverType"));
            crawlConfig.setStockTicker(props.getProperty("stockTicker"));

            crawlConfig.setChromeDriverLocation(props.getProperty("webdriver.chrome.driver"));
            crawlConfig.setFirefoxDriverLocation(props.getProperty("webdriver.firefox.driver"));


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return crawlConfig;
    }
}
