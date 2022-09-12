package com.example.todolistapp.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;


    public List<ToDoList> getAllLists(){
        List<ToDoList> lists = new ArrayList<>();
        toDoListRepository.findAll().forEach(lists::add);
        return lists;
    }

    public ToDoList getListByName(String listName){
        return toDoListRepository.findByName(listName).get(0);
    }

    public void addList(ToDoList list){
        toDoListRepository.save(list);
    }

    public void deleteListByName(String listName){
        ToDoList list = toDoListRepository.findByName(listName).get(0);
        toDoListRepository.delete(list);
    }

    public void addItem(ToDoList list){
        toDoListRepository.save(list);
    }

    public void deleteItem(ToDoList list){
        toDoListRepository.save(list);
    }

}
