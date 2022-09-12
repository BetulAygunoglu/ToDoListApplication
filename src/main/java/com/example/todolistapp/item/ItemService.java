package com.example.todolistapp.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(){
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Item getItemByName(String itemName){
        return itemRepository.findByName(itemName).get(0);
    }

    public void updateItem(String itemName) {
        itemRepository.save(getItemByName(itemName));
    }

    public void completeItem(String itemName){
        Item item = getItemByName(itemName);
        item.setCompleted(true);
        item.setStatus(HttpStatus.IM_USED);
        itemRepository.save(item);
    }

    public List<Item> orderItemsByName(String name){
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, name));
    }


}
