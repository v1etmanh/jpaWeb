package com.jpd.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpd.model.Customer;
import com.jpd.model.RememberWord;

@Repository
public interface RememberWordRepository extends CrudRepository<RememberWord, Long> {
List<RememberWord> findByCustomer(Customer customer);
long deleteByRwId(long rwId);
}
