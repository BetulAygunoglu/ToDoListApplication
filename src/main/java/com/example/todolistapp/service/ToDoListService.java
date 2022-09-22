package com.example.todolistapp.service;

import com.example.todolistapp.dto.ToDoListDto;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.repository.ToDoListRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public ToDoListService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<ToDoListDto> getAllLists(){
        List<ToDoList> lists = new ArrayList<>();
        toDoListRepository.findAll().forEach(lists::add);
        List<ToDoListDto> toDoListDtos = lists.stream().map(list -> modelMapper.map(list, ToDoListDto.class)).collect(Collectors.toList());
        return toDoListDtos;
    }

    public ToDoListDto getListByName(String listName) throws NoSuchElementException{
        if(handleNoSuchElementEx(listName)){
            throw new NoSuchElementException();
        }
        return modelMapper.map(toDoListRepository.findByName(listName).get(0), ToDoListDto.class);
    }

//    public void addList(ToDoListDto listDto) throws NullPointerException {
//        ToDoList list = modelMapper.map(listDto, ToDoList.class);
//        if(handleNullPointerEx(list)){
//            throw new NullPointerException();
//        }
//        toDoListRepository.save(list);
//    }

    public void deleteListByName(String listName) throws NoSuchElementException{
        if(handleNoSuchElementEx(listName)){
            throw new NoSuchElementException();
        }
        ToDoList list = toDoListRepository.findByName(listName).get(0);
        toDoListRepository.delete(list);
    }

    public void addItem(ToDoListDto toDoListDto){
        ToDoList toDoList = modelMapper.map(toDoListDto, ToDoList.class);
        toDoListRepository.save(toDoList);
    }

    public void deleteItem(ToDoListDto toDoListDto){
        ToDoList toDoList = modelMapper.map(toDoListDto, ToDoList.class);
        toDoListRepository.save(toDoList);
    }

    public List<ToDoList> orderListsByName(String field){
        List<ToDoList> toDoLists = toDoListRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return toDoLists;
    }


//    public boolean handleNullPointerEx(ToDoList list){
//        if(list.getName().equals(null) || list.getName().length() == 0){
//           return true;
//        }
//        return false;
//    }

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
