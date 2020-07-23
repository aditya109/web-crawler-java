package com.webcrawl.core;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class WebScrape {
    public static void main(String[] args) throws IOException {
        System.out.println("==> ==> ==> ==> Starting www ...\n\n");

        //  creating `Summary` object
        Summary summary = new Summary();

        //  creating `ConsentingInvestmentParticipants` object
        ConsentingInvestorParticipants consentingInvestorParticipants = new ConsentingInvestorParticipants();

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

        List<String> shareholdingCCASSValList = new ArrayList<>();

        for(Element value : shareholdingCCASSVal) {
            shareholdingCCASSValList.add(value.text());
        }

        Elements numberOfParticipants = summaryTable.select("div.ccass-search-datarow div.number-of-participants div.value");

        List<String> numberOfParticipantsList = new ArrayList<>();

        for(Element value : numberOfParticipants) {
            numberOfParticipantsList.add(value.text());
        }

        Elements percentageOfParticipants = summaryTable.select("div.ccass-search-datarow div.percent-of-participants div.value");

        List<String> percentageOfParticipantsList = new ArrayList<>();

        for(Element value : percentageOfParticipants) {
            percentageOfParticipantsList.add(value.text());
        }
        List<Map<String, String>> tempList = new ArrayList<>();
        for(int idx = 0 ; idx < 3; idx++) {
            Map<String, String> row = new HashMap<String, String>();
            row.put("Shareholding in CCASS", shareholdingCCASSValList.get(idx));
            row.put("Number of Participants", numberOfParticipantsList.get(idx));
            row.put("% of the total number of Issued Shares/ Warrants/ Units", percentageOfParticipantsList.get(idx));
            tempList.add(row);
        }
        summary.setMarketIntermediaries(tempList.get(0));
        summary.setNonConsentingInvestorParticipants(tempList.get(1));
        summary.setTotal(tempList.get(2));

        Elements lastUpdateCCASS = summaryTable.select("div.ccass-search-remarks");
        Map<String, String> tempMap = new HashMap<String, String>();
        Elements lastUpdateTitleCCASS = lastUpdateCCASS.select("div.summary-header");
        Elements lastUpdateValueCCASS = lastUpdateCCASS.select("div.summary-value");
        tempMap.put(lastUpdateTitleCCASS.text(), lastUpdateValueCCASS.text());
        summary.setTotalIssuesLastUpdate(tempMap);

        tempList = new ArrayList<>();
        Elements tableOfParticipant = document.select("table.table-scroll.table-sort.table-mobile-list tbody");
        for(Element row : tableOfParticipant.select("tr")) {
            tempMap = new HashMap<String, String>();
            tempMap.put(row.select("td.col-participant-id div.mobile-list-heading").text(), row.select("td.col-participant-id div.mobile-list-body").text());
            tempMap.put(row.select("td.col-participant-name div.mobile-list-heading").text(), row.select("td.col-participant-name div.mobile-list-body").text());
            tempMap.put(row.select("td.col-address div.mobile-list-heading").text(), row.select("td.col-address div.mobile-list-body").text());
            tempMap.put(row.select("td.col-shareholding.text-right div.mobile-list-heading").text(), row.select("td.col-shareholding.text-right div.mobile-list-body").text());
            tempMap.put(row.select("td.col-shareholding-percent.text-right div.mobile-list-heading").text(), row.select("td.col-shareholding-percent.text-right div.mobile-list-body").text());

            tempList.add(tempMap);
        }

        consentingInvestorParticipants.setTable(tempList);

        ObjectMapper mapper = new ObjectMapper();

        String summaryJSONString = mapper.writeValueAsString(summary);

        String consentingInvestorParticipantsJSONString = mapper.writeValueAsString(consentingInvestorParticipants.getTable());

        //  converted tableJSON to csv as `table.csv`
        JsonNode tableJSON = new ObjectMapper().readTree(consentingInvestorParticipantsJSONString);

        Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = tableJSON.elements().next();
        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File("src/main/resources/table.csv"), tableJSON);

        FileWriter file=null;

        try {
            file = new FileWriter("src/main/resources/summary.csv");
            CSVUtils.writeLine(file, Collections.singletonList(summaryJSONString));

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            file.flush();
            file.close();
        }


    }
}
//mvn clean package ; java -jar target/intro-1.0-SNAPSHOT.jar 123456
