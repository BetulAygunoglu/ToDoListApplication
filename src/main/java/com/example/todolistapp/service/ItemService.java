package com.example.todolistapp.service;

import com.example.todolistapp.model.Item;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(){
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Item getItemByName(String itemName) throws NoSuchElementException {
        if(handleNoSuchElementEx(itemName)){
            throw new NoSuchElementException();
        }
        return itemRepository.findByItemName(itemName).get(0);
    }

    public void updateItem(String itemName){
        itemRepository.save(getItemByName(itemName));
    }

    public void completeItem(String itemName){
        Item item = getItemByName(itemName);
        item.setCompleted(true);
        item.setStatus(HttpStatus.IM_USED);
        itemRepository.save(item);
    }

    public List<Item> orderItemsByName(String itemName){
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, itemName));
    }

    public boolean handleNoSuchElementEx(String itemName){
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        for(Item item:items){
            if(itemName.equals(item.getItemName())){
                return false;
            }
        }
        return true;
    }


}
