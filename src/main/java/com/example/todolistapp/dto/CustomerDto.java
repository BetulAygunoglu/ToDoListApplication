package com.example.todolistapp.dto;


import com.example.todolistapp.model.ToDoList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDto {

    private Long id;
    private String customerName;
    private List<ToDoList> customersLists = new ArrayList<>();

}
