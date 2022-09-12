package com.example.todolistapp.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/items")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @RequestMapping("/items/{itemName}")
    public Item getItemByName(@PathVariable String itemName){
        return itemService.getItemByName(itemName);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/items/{itemName}/description/{description}")
    public void updateItemDescription(@PathVariable String itemName, @PathVariable String description){
        itemService.getItemByName(itemName).setDescription(description);
        itemService.updateItem(itemName);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/items/{itemName}/deadline/{deadline}")
    public void updateItemDeadline(@PathVariable String itemName, @PathVariable Date deadline){
        itemService.getItemByName(itemName).setDeadline(deadline);
        itemService.updateItem(itemName);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/items/{itemName}/markcomplete")
    public void completeItem(@PathVariable String itemName){
        itemService.completeItem(itemName);
    }


}
