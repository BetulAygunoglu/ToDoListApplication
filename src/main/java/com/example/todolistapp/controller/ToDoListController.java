package com.example.todolistapp.controller;

import com.example.todolistapp.dto.ItemDto;
import com.example.todolistapp.dto.ToDoListDto;
import com.example.todolistapp.model.Item;
import com.example.todolistapp.service.ItemService;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.service.ToDoListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private ItemService itemService;
    private final ModelMapper modelMapper;

    public ToDoListController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/lists")
    public List<ToDoListDto> getAllLists(){
        return toDoListService.getAllLists();
    }

    @GetMapping("/lists/{listName}")
    public ToDoListDto getListByName(@Valid @PathVariable String listName){
        return toDoListService.getListByName(listName);
    }

//    @PostMapping("/lists")
//    public void addList(@RequestBody ToDoListDto listDto){
//        toDoListService.addList(listDto);
//    }

    @DeleteMapping("/lists/{listName}")
    public void deleteListByName(@Valid @PathVariable String listName) {
        toDoListService.deleteListByName(listName);
    }

    @PutMapping("/lists/{listName}/items/name/{newItemName}/desc/{description}/date/{date}")
    public void addItem(@Valid @PathVariable String listName, @Valid @PathVariable String newItemName, @PathVariable String description, @PathVariable Date date){
        ToDoList toDoList = modelMapper.map(getListByName(listName), ToDoList.class);
        toDoList.getListItems().add(new Item(newItemName,description,date,false, HttpStatus.CREATED));
        ToDoListDto toDoListDto = modelMapper.map(toDoList, ToDoListDto.class);
        toDoListService.addItem(toDoListDto);
}

    @DeleteMapping( "/lists/{listName}/items/{itemName}")
    public void deleteItem(@Valid @PathVariable String listName, @Valid @PathVariable String itemName){
        ToDoList toDoList = modelMapper.map(getListByName(listName), ToDoList.class);
        ItemDto itemDto = itemService.getItemByName(itemName);
        Item item = modelMapper.map(itemDto, Item.class);
        toDoList.getListItems().remove(item);
        ToDoListDto toDoListDto = modelMapper.map(toDoList, ToDoListDto.class);
        toDoListService.deleteItem(toDoListDto);
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

    @GetMapping( "/lists/{listName}/orderitemsby/{field}")
    public List<ItemDto> orderItemsByName(@Valid @PathVariable String listName, @PathVariable String field) {
        ToDoList toDoList = modelMapper.map(getListByName(listName), ToDoList.class);
        List<Item> listItems = toDoList.getListItems();
        List<Item> orderedItems = itemService.orderItemsByName(field);
            for (Item item : orderedItems) {
              if (!listItems.contains(item)) {
                   orderedItems.remove(item);
              }
          }
            return orderedItems.stream().map(item -> modelMapper.map(item, ItemDto.class)).collect(Collectors.toList());
    }



}
