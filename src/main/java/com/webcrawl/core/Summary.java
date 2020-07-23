package com.webcrawl.core;

import java.util.HashMap;
import java.util.Map;

public class Summary {
    Map<String, Integer> marketIntermediaries = new HashMap<String, Integer>();
    Map<String, Integer> nonConsentingInvestorParticipants = new HashMap<String, Integer>();
    Map<String, Integer> Total = new HashMap<String, Integer>();
    int totalNumberOfIssue = 0;

    public Map<String, Integer> getMarketIntermediaries() {
        return marketIntermediaries;
    }

    public void setMarketIntermediaries(Map<String, Integer> marketIntermediaries) {
        this.marketIntermediaries = marketIntermediaries;
    }

    public Map<String, Integer> getNonConsentingInvestorParticipants() {
        return nonConsentingInvestorParticipants;
    }

    public void setNonConsentingInvestorParticipants(Map<String, Integer> nonConsentingInvestorParticipants) {
        this.nonConsentingInvestorParticipants = nonConsentingInvestorParticipants;
    }

    public Map<String, Integer> getTotal() {
        return Total;
    }

    public void setTotal(Map<String, Integer> total) {
        Total = total;
    }

    public int getTotalNumberOfIssue() {
        return totalNumberOfIssue;
    }

    public void setTotalNumberOfIssue(int totalNumberOfIssue) {
        this.totalNumberOfIssue = totalNumberOfIssue;
    }
}
