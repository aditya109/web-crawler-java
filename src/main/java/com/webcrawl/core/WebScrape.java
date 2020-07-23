package com.webcrawl.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.Map;


public class WebScrape {
    public static void main(String[] args) throws IOException {
        System.out.println("==> ==> ==> ==> \uD83D\uDE80\n\n");

        //  getting the config object
        CrawlConfig crawlConfig = new CrawlConfigBuilder().getCrawlConfig();

        //  initialising the web driver headless
        WebDriver driver = null;

        try {
            switch (crawlConfig.getDriverType().toLowerCase()) {
                case "chrome":
                    //  configuring webdriver as chrome
                    System.setProperty("webdriver.chrome.driver", crawlConfig.getChromeDriverLocation());
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    //  configuring webdriver as firefox
                    System.setProperty("webdriver.firefox.driver", crawlConfig.getFirefoxDriverLocation());
                    driver = new FirefoxDriver();
                default:
                    //  configuring webdriver-default as chrome in case of problems
                    System.setProperty("webdriver.chrome.driver", crawlConfig.getChromeDriverLocation());
                    driver = new ChromeDriver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  getting url
        driver.get(crawlConfig.getUrl());

        //  finding stock code in the page
        WebElement txtStockCode = driver.findElement(By.xpath("//*[@id=\"txtStockCode\"]"));
        //  setting the stock code
        txtStockCode.sendKeys(crawlConfig.getStockTicker());

        //  selecting `search` button
        WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\"btnSearch\"]"));
        //  clicking `search` button
        searchBtn.click();
        final Document document = Jsoup.parse(driver.getPageSource());

        Elements summaryTable = document.select("div.ccass-search-summary-table");
        Elements shareholdingCCASSVal = summaryTable.select("div.ccass-search-datarow div.shareholding div.value");

        for(Element v : shareholdingCCASSVal) {
            System.out.println(v.text());
        }






    }
}
//mvn clean package ; java -jar target/intro-1.0-SNAPSHOT.jar 123456
