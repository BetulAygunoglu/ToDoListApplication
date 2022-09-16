package com.example.todolistapp.controller;

import com.example.todolistapp.model.Customer;
import com.example.todolistapp.model.ToDoList;
import com.example.todolistapp.request.CustomerRequest;
import com.example.todolistapp.service.CustomerService;
import com.example.todolistapp.service.ToDoListService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    private CustomerService customerService;
    private ToDoListService toDoListService;

    public CustomerController(CustomerService customerService, ToDoListService toDoListService) {
        this.customerService = customerService;
        this.toDoListService = toDoListService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id){
        return customerService.getCustomer(id);
    }
    @PostMapping("/customers")
    public void addCustomer(@RequestBody CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customerService.addCustomer(customer);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

    @PutMapping("/customers/{id}/lists/{newListName}/items/{items}")
    public void addToDoList(@PathVariable Long id, @Valid @PathVariable String newListName){
        getCustomer(id).getCustomersLists().add(new ToDoList(newListName));
        customerService.addToDoList(getCustomer(id));
    }

    @DeleteMapping("/customers/{id}/lists/{listName}")
    public void deleteToDoList(@PathVariable Long id, @Valid @PathVariable String listName){
        getCustomer(id).getCustomersLists().remove(toDoListService.getListByName(listName));
        customerService.deleteToDoList(getCustomer(id));
    }

    @GetMapping( "/customers/{id}/orderby/{field}")
    public List<ToDoList> orderListsByName(@Valid @PathVariable Long id, @PathVariable String field) {
        List<ToDoList> customersLists = getCustomer(id).getCustomersLists();
        List<ToDoList> orderedLists = toDoListService.orderListsByName(field);;
        for (ToDoList toDoList : orderedLists) {
            if (!customersLists.contains(toDoList)) {
                orderedLists.remove(toDoList);
            }
        }

        return orderedLists;
    }

}
