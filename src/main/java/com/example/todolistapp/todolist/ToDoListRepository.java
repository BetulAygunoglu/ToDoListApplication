package com.example.todolistapp.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, String> {
    List<ToDoList> findByName(String listName);


}
