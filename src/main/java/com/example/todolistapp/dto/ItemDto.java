package com.example.todolistapp.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Date;

@Data
public class ItemDto {

    private String itemName;
    private String description;
    private Date deadline;
    private boolean completed = Boolean.FALSE;
    private HttpStatus status;

}
