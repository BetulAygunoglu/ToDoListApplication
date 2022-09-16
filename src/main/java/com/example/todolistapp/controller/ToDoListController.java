package com.example.todolistapp.controller;

import com.example.todolistapp.model.Item;
import com.example.todolistapp.service.ItemService;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.*;

@RestController
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/lists")
    public List<ToDoList> getAllLists(){
        return toDoListService.getAllLists();
    }

    @GetMapping("/lists/{listName}")
    public ToDoList getListByName(@Valid @PathVariable String listName){
        return toDoListService.getListByName(listName);
    }

    @PostMapping("/lists")
    public void addList(@RequestBody ToDoList list){
        toDoListService.addList(list);
    }

    @DeleteMapping("/lists/{listName}")
    public void deleteListByName(@Valid @PathVariable String listName) {
        toDoListService.deleteListByName(listName);
    }

    @PutMapping("/lists/{listName}/items/name/{newItemName}/desc/{description}/date/{date}")
    public void addItem(@Valid @PathVariable String listName, @Valid @PathVariable String newItemName, @PathVariable String description, @PathVariable Date date){
        getListByName(listName).getListItems().add(new Item(newItemName,description,date,false, HttpStatus.CREATED));
        toDoListService.addItem(getListByName(listName));
}

    @DeleteMapping( "/lists/{listName}/items/{itemName}")
    public void deleteItem(@Valid @PathVariable String listName, @Valid @PathVariable String itemName){
        getListByName(listName).getListItems().remove(itemService.getItemByName(itemName));
        toDoListService.deleteItem(getListByName(listName));
    }

    @GetMapping( "/lists/{listName}/filterstatuscomplete")
    public List<Item> filterItemsStatusComplete(@Valid @PathVariable String listName) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> filteredItems = new ArrayList<>();
        items.stream().
                filter(item -> statusIsComplete(item.getItemName())).forEach(filteredItems::add);

        return filteredItems;
    }

    @GetMapping( "/lists/{listName}/filterstatusnotcomplete")
    public List<Item> filterItemsStatusNotComplete(@Valid @PathVariable String listName) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> filteredItems = new ArrayList<>();
        items.stream().
                filter(item -> !statusIsComplete(item.getItemName())).forEach(filteredItems::add);

        return filteredItems;
    }

    public boolean statusIsComplete(String itemName){
        return itemService.getItemByName(itemName).getStatus().equals(HttpStatus.IM_USED);
    }

    @GetMapping( "/lists/{listName}/filteritemsexpired")
    public List<Item> filterItemsExpired(@Valid @PathVariable String listName) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> filteredItems = new ArrayList<>();
        items.stream().
                filter(item -> itemExpired(item.getItemName())).forEach(filteredItems::add);

        return filteredItems;
    }

    public boolean itemExpired(String itemName){
        long time = System.currentTimeMillis();
        java.sql.Date date = new Date(time);
        if(!statusIsComplete(itemName) && itemService.getItemByName(itemName).getDeadline().before(date))
            return true;

        return false;
    }

    @GetMapping( "/lists/{listName}/orderby/{field}")
    public List<Item> orderItemsByName(@Valid @PathVariable String listName, @PathVariable String field) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> orderedItems = itemService.orderItemsByName(field);;
        for (Item item : orderedItems) {
            if (!items.contains(item)) {
                orderedItems.remove(item);
            }
        }

        return orderedItems;
    }



}
