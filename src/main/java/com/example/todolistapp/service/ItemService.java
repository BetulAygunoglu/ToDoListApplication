package com.example.todolistapp.service;

import com.example.todolistapp.dto.ItemDto;
import com.example.todolistapp.model.Item;
import com.example.todolistapp.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<ItemDto> getAllItems(){
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        List<ItemDto> itemDtos = items.stream().map(item -> modelMapper.map(item, ItemDto.class)).collect(Collectors.toList());
        return itemDtos;
    }

    public ItemDto getItemByName(String itemName) throws NoSuchElementException {
        if(handleNoSuchElementEx(itemName)){
            throw new NoSuchElementException();
        }
        return modelMapper.map(itemRepository.findByItemName(itemName).get(0), ItemDto.class);
    }

    public void updateItemDescription(String itemName,String description){
        ItemDto itemDto = getItemByName(itemName);
        Item item = modelMapper.map(itemDto, Item.class);
        item.setDescription(description);
        itemRepository.save(item);
    }

    public void updateItemDeadline(String itemName, Date deadline){
        ItemDto itemDto = getItemByName(itemName);
        Item item = modelMapper.map(itemDto, Item.class);
        item.setDeadline(deadline);
        itemRepository.save(item);
    }

    public void completeItem(String itemName){
        ItemDto itemDto = getItemByName(itemName);
        Item item = modelMapper.map(itemDto, Item.class);
        item.setCompleted(true);
        item.setStatus(HttpStatus.IM_USED);
        itemRepository.save(item);
    }

    public List<Item> orderItemsByName(String field){
        List<Item> orderedItems = itemRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return orderedItems;
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
