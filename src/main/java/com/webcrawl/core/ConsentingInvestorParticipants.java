package com.webcrawl.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsentingInvestorParticipants {
    public List<Map<String, String>> getTable() {
        return table;
    }

    public void setTable(List<Map<String, String>> table) {
        this.table = table;
    }

    List<Map<String, String>> table = new ArrayList<>();
}
