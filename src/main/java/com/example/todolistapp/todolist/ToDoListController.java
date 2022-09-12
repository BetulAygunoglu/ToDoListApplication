package com.example.todolistapp.todolist;

import com.example.todolistapp.item.Item;
import com.example.todolistapp.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@RestController
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private ItemService itemService;
    private ToDoList listByName;

    @RequestMapping("/lists")
    public List<ToDoList> getAllLists(){
        return toDoListService.getAllLists();
    }

    @RequestMapping("/lists/{listName}")
    public ToDoList getListByName(@PathVariable String listName){
        return toDoListService.getListByName(listName);
    }

    @RequestMapping(method = RequestMethod.POST, value="/lists")
    public void addList(@RequestBody ToDoList list){
        toDoListService.addList(list);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/lists/{listName}")
    public void deleteListByName(@PathVariable String listName){
        toDoListService.deleteListByName(listName);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/lists/{listName}/items/{newItemName}")
    public void addItem(@PathVariable String listName, @PathVariable String newItemName){
        getListByName(listName).getListItems().add(new Item(newItemName,"",null,false, HttpStatus.CREATED));
        toDoListService.addItem(getListByName(listName));
}

    @RequestMapping(method = RequestMethod.DELETE, value = "/lists/{listName}/items/{itemName}")
    public void deleteItem(@PathVariable String listName, @PathVariable String itemName){
        getListByName(listName).getListItems().remove(itemService.getItemByName(itemName));
        toDoListService.deleteItem(getListByName(listName));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lists/{listName}/filterstatuscomplete")
    public List<Item> filterItemsStatusComplete(@PathVariable String listName) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> filteredItems = new ArrayList<>();
        items.stream().
                filter(item -> statusIsComplete(item.getName())).forEach(filteredItems::add);

        return filteredItems;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lists/{listName}/filterstatusnotcomplete")
    public List<Item> filterItemsStatusNotComplete(@PathVariable String listName) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> filteredItems = new ArrayList<>();
        items.stream().
                filter(item -> !statusIsComplete(item.getName())).forEach(filteredItems::add);

        return filteredItems;
    }

    public boolean statusIsComplete(String itemName){
        return itemService.getItemByName(itemName).getStatus().equals(HttpStatus.IM_USED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lists/{listName}/filteritemsexpired")
    public List<Item> filterItemsExpired(@PathVariable String listName) {
        List<Item> items = getListByName(listName).getListItems();
        List<Item> filteredItems = new ArrayList<>();
        items.stream().
                filter(item -> itemExpired(item.getName())).forEach(filteredItems::add);

        return filteredItems;
    }

    public boolean itemExpired(String itemName){
        long time = System.currentTimeMillis();
        java.sql.Date date = new Date(time);
        if(!statusIsComplete(itemName) && itemService.getItemByName(itemName).getDeadline().before(date))
            return true;

        return false;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lists/{listName}/orderby/{field}")
    public List<Item> orderItemsByName(@PathVariable String listName, @PathVariable String field) {
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
