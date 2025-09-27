package com.hotel.hotel.Service;

import com.hotel.hotel.Model.Customer;

import java.util.List;

public interface CustomerServices {


    List<Customer> GetAllCustomer();
    Customer FindCustomerById(int customerId);
    void SaveCustomer(Customer customer);
    void DeleteCustomer(Customer customer);

}
