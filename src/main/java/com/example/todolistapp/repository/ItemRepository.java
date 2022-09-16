package com.example.todolistapp.repository;

import com.example.todolistapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByItemName(String itemName);

}
