package com.example.todolistapp.controller;

import com.example.todolistapp.dto.ItemDto;
import com.example.todolistapp.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;
    private final ModelMapper modelMapper;

    public ItemController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/items")
    public List<ItemDto> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/items/{itemName}")
    public ItemDto getItemByName(@PathVariable String itemName) {
        return itemService.getItemByName(itemName);
    }

    @PutMapping( "/items/{itemName}/description/{description}")
    public void updateItemDescription(@Valid @PathVariable String itemName, @PathVariable String description){
        itemService.updateItemDescription(itemName, description);
    }

    @PutMapping( "/items/{itemName}/deadline/{deadline}")
    public void updateItemDeadline(@Valid @PathVariable String itemName, @PathVariable Date deadline){
        itemService.updateItemDeadline(itemName, deadline);
    }

    @PutMapping( "/items/{itemName}/markcomplete")
    public void completeItem(@Valid @PathVariable String itemName){
        itemService.completeItem(itemName);
    }


}
