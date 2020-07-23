package com.webcrawl.core;

import java.util.HashMap;
import java.util.Map;

public class Summary {
    Map<String, String> marketIntermediaries = new HashMap<String, String>();
    Map<String, String> nonConsentingInvestorParticipants = new HashMap<String, String>();
    Map<String, String> total = new HashMap<String, String>();

    public Map<String, String> getTotalIssuesLastUpdate() {
        return totalIssuesLastUpdate;
    }

    public void setTotalIssuesLastUpdate(Map<String, String> totalIssuesLastUpdate) {
        this.totalIssuesLastUpdate = totalIssuesLastUpdate;
    }

    Map<String, String> totalIssuesLastUpdate = new HashMap<String, String>();

    public Map<String, String> getMarketIntermediaries() {
        return marketIntermediaries;
    }

    public void setMarketIntermediaries(Map<String, String> marketIntermediaries) {
        this.marketIntermediaries = marketIntermediaries;
    }

    public Map<String, String> getNonConsentingInvestorParticipants() {
        return nonConsentingInvestorParticipants;
    }

    public void setNonConsentingInvestorParticipants(Map<String, String> nonConsentingInvestorParticipants) {
        this.nonConsentingInvestorParticipants = nonConsentingInvestorParticipants;
    }

    public Map<String, String> getTotal() {
        return total;
    }

    public void setTotal(Map<String, String> total) {
        this.total = total;
    }


}
