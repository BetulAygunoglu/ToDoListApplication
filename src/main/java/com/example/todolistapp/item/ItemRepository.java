package com.example.todolistapp.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByName(String itemName);

}
