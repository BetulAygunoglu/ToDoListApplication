package com.example.todolistapp.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Entity
@Getter
@Setter
@ToString
public class Item {

    @Id
    private String name;
    private String description;
    private Date deadline;
    private boolean completed = Boolean.FALSE;
    private HttpStatus status;


    protected Item() {
    }


    public Item(String name, String description, Date deadline, Boolean completed, HttpStatus status) {
        super();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.completed = completed;
        this.status = status;
    }

}
