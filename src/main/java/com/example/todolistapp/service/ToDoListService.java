package com.example.todolistapp.service;

import com.example.todolistapp.model.Item;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    public List<ToDoList> getAllLists(){
        List<ToDoList> lists = new ArrayList<>();
        toDoListRepository.findAll().forEach(lists::add);
        return lists;
    }

    public ToDoList getListByName(String listName) throws NoSuchElementException{
        if(handleNoSuchElementEx(listName)){
            throw new NoSuchElementException();
        }
        return toDoListRepository.findByName(listName).get(0);
    }

    public void addList(ToDoList list) throws NullPointerException {
        if(handleNullPointerEx(list)){
            throw new NullPointerException();
        }
        toDoListRepository.save(list);
    }

    public void deleteListByName(String listName) throws NoSuchElementException{
        if(handleNoSuchElementEx(listName)){
            throw new NoSuchElementException();
        }
        ToDoList list = toDoListRepository.findByName(listName).get(0);
        toDoListRepository.delete(list);
    }

    public void addItem(ToDoList list){
        toDoListRepository.save(list);
    }

    public void deleteItem(ToDoList list){
        toDoListRepository.save(list);
    }

    public List<ToDoList> orderListsByName(String listName){
        return toDoListRepository.findAll(Sort.by(Sort.Direction.ASC, listName));
    }

    public boolean handleNullPointerEx(ToDoList list){
        if(list.getName().equals(null) || list.getName().length() == 0){
           return true;
        }
        return false;
    }

    public boolean handleNoSuchElementEx(String listName){
        List<ToDoList> lists = new ArrayList<>();
        toDoListRepository.findAll().forEach(lists::add);
        for(ToDoList list:lists){
            if(listName.equals(list.getName())){
                return false;
            }
        }
        return true;
    }

}
