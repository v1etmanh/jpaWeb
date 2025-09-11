package com.jpd.repository;

import org.springframework.data.repository.CrudRepository;

import com.jpd.model.RequestSpeaking;
import com.jpd.model.Customer;
import java.util.List;


public interface RequestSpeakingRepository extends CrudRepository<RequestSpeaking,Long> {
  RequestSpeaking  findByCustomer(Customer customer);
}
