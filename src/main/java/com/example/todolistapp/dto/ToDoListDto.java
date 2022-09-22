package com.example.todolistapp.dto;

import com.example.todolistapp.model.Item;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ToDoListDto {

    private String name;
    private List<Item> listItems = new ArrayList<>();

}
