package com.company.search.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    String name;

    public Item(String name) {
        this.name = name;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }
}
