package com.example.todolistapp.controller;

import com.example.todolistapp.dto.CustomerDto;
import com.example.todolistapp.dto.ToDoListDto;
import com.example.todolistapp.model.Customer;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.request.CustomerRequest;
import com.example.todolistapp.service.CustomerService;
import com.example.todolistapp.service.ToDoListService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private CustomerService customerService;
    private ToDoListService toDoListService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ToDoListService toDoListService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.toDoListService = toDoListService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable Long id){
        return customerService.getCustomer(id);
    }
    @PostMapping("/customers")
    public void addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName(customerRequest.getCustomerName());
        customerService.addCustomer(customerDto);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

    @PutMapping("/customers/{id}/lists/{newListName}")
    public void addToDoList(@PathVariable Long id, @Valid @PathVariable String newListName){
        Customer customer = modelMapper.map(getCustomer(id), Customer.class);
        customer.getCustomersLists().add(new ToDoList(newListName));
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        customerService.addToDoList(customerDto);
    }

    @DeleteMapping("/customers/{id}/lists/{listName}")
    public void deleteToDoList(@PathVariable Long id, @Valid @PathVariable String listName){
        Customer customer = modelMapper.map(getCustomer(id), Customer.class);
        ToDoList toDoList = modelMapper.map(toDoListService.getListByName(listName), ToDoList.class);
        customer.getCustomersLists().remove(toDoList);
        customerService.deleteToDoList(id);
    }

    @GetMapping( "/customers/{id}/orderlistsby/{field}")
    public List<ToDoListDto> orderListsByName(@Valid @PathVariable Long id, @PathVariable String field) {
        Customer customer = modelMapper.map(getCustomer(id), Customer.class);
        List<ToDoList> customersLists = customer.getCustomersLists();
        List<ToDoList> orderedLists = toDoListService.orderListsByName(field);
        for (ToDoList toDoList : orderedLists) {
            if (!customersLists.contains(toDoList)) {
                orderedLists.remove(toDoList);
            }
        }

        return orderedLists.stream().map(list -> modelMapper.map(list, ToDoListDto.class)).collect(Collectors.toList());
    }


}
