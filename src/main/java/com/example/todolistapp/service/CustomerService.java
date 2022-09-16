package com.example.todolistapp.service;

import com.example.todolistapp.model.Customer;
import com.example.todolistapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    public Customer getCustomer(Long id) throws NoSuchElementException{
        if(handleNoSuchElementEx(id)){
            throw new NoSuchElementException();
        }
        return customerRepository.findById(id).get();
    }

    public void addCustomer(Customer customer) throws NullPointerException{
        if(handleNullPointerEx(customer)){
            throw new NoSuchElementException();
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) throws NoSuchElementException{
        if(handleNoSuchElementEx(id)){
            throw new NoSuchElementException();
        }
        customerRepository.deleteById(id);
    }

    public void addToDoList(Customer customer){
        customerRepository.save(customer);
    }

    public void deleteToDoList(Customer customer){
        customerRepository.save(customer);
    }

    public boolean handleNullPointerEx(Customer customer){
        if(customer.getCustomerName().equals(null) || customer.getCustomerName().length() == 0){
            return true;
        }
        return false;
    }

    public boolean handleNoSuchElementEx(Long id){
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        for(Customer customer:customers){
            if(id.equals(customer.getId())){
                return false;
            }
        }
        return true;
    }


}
