package com.example.todolistapp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private String CustomerName;

    public CustomerRequest() {
    }

    public CustomerRequest(String customerName) {
        CustomerName = customerName;
    }

}
