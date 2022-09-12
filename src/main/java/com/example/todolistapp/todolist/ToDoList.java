package com.example.todolistapp.todolist;

import com.example.todolistapp.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class ToDoList {

    @Id
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "todolist_name")
    private List<Item> listItems = new ArrayList<>();

    protected ToDoList() {
    }

    public ToDoList(String name) {
        super();
        this.name = name;
    }

    public ToDoList(String name, List<Item> listItems) {
        super();
        this.name = name;
        this.listItems = listItems;
    }

}
