package com.example.todolistapp.service;

import com.example.todolistapp.dto.CustomerDto;
import com.example.todolistapp.model.Customer;
import com.example.todolistapp.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public List<CustomerDto> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        List<CustomerDto> customerDtos = customers.stream().map(customer -> modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
        return customerDtos;
    }

    public CustomerDto getCustomer(Long id) throws NoSuchElementException{
        if(handleNoSuchElementEx(id)){
            throw new NoSuchElementException();
        }
        return modelMapper.map(customerRepository.findById(id).get(), CustomerDto.class);
    }

    public void addCustomer(CustomerDto customerDto) throws NullPointerException{
        Customer customer = modelMapper.map(customerDto, Customer.class);
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

    public void addToDoList(CustomerDto customerDto){
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerRepository.save(customer);
    }

    public void deleteToDoList(Long id){
        CustomerDto customerDto = getCustomer(id);
        Customer customer = modelMapper.map(customerDto, Customer.class);
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
