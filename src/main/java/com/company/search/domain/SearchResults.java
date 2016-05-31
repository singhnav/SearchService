package com.company.search.domain;

import com.company.search.database.Item;

import java.util.List;

public class SearchResults {
    List<Item> items;

    public SearchResults(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
