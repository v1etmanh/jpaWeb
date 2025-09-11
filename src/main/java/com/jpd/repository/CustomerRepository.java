package com.jpd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpd.model.Customer;
import java.util.List;

@Repository
public interface  CustomerRepository  extends CrudRepository<Customer, Long>{
Customer findCustomerByEmail(String email);
}
