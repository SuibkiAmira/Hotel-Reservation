package com.hotel.hotel.Repository;

import com.hotel.hotel.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findByCustomerId(int customerId);
}
