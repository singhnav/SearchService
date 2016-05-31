package com.company.search.service;

import com.company.search.repository.ItemsRepository;
import com.company.search.database.Item;
import com.company.search.domain.SearchResults;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchService {

    @Resource
    ItemsRepository itemsRepository;

    public SearchResults getAll(){
        List<Item> itemList = itemsRepository.getAll();
        return new SearchResults(itemList);
    }

    public SearchResults findByName(final String name){
        List<Item> itemList = itemsRepository.findByName(name);
        return new SearchResults(itemList);
    }

    public void save(Item item) {
        itemsRepository.save(item);
    }

    public void deleteTable() {
        itemsRepository.deleteTable();
    }
}
