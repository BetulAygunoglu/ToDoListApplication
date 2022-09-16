package com.example.todolistapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter
@Setter
@ToString
public class Item {

    @Id
    private String itemName;
    private String description;
    private Date deadline;
    private boolean completed = Boolean.FALSE;
    private HttpStatus status;


    protected Item() {
    }


    public Item(String itemName, String description, Date deadline, Boolean completed, HttpStatus status) {
        super();
        this.itemName = itemName;
        this.description = description;
        this.deadline = deadline;
        this.completed = completed;
        this.status = status;
    }

}
