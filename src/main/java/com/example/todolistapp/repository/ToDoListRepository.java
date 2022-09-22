package com.example.todolistapp.repository;

import com.example.todolistapp.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, String> {

    List<ToDoList> findByName(String listName);

}
