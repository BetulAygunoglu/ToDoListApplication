package com.example.todolistapp.controller;

import com.example.todolistapp.service.ItemService;
import com.example.todolistapp.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/items/{itemName}")
    public Item getItemByName(@PathVariable String itemName) {
        return itemService.getItemByName(itemName);
    }

    @PutMapping( "/items/{itemName}/description/{description}")
    public void updateItemDescription(@Valid @PathVariable String itemName, @PathVariable String description){
        itemService.getItemByName(itemName).setDescription(description);
        itemService.updateItem(itemName);
    }

    @PutMapping( "/items/{itemName}/deadline/{deadline}")
    public void updateItemDeadline(@Valid @PathVariable String itemName, @PathVariable Date deadline){
        itemService.getItemByName(itemName).setDeadline(deadline);
        itemService.updateItem(itemName);
    }

    @PutMapping( "/items/{itemName}/markcomplete")
    public void completeItem(@Valid @PathVariable String itemName){
        itemService.completeItem(itemName);
    }


}
