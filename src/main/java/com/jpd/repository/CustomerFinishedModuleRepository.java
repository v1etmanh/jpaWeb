package com.jpd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jpd.model.Customer;
import com.jpd.model.Module;
import com.jpd.model.CustomerFinishedModule;
import com.jpd.model.Enrollment;

public interface CustomerFinishedModuleRepository extends CrudRepository<CustomerFinishedModule, Long> {
 List<CustomerFinishedModule> findAll();
List<CustomerFinishedModule> findByCustomer(Customer customer);
Optional<CustomerFinishedModule> findByCustomerAndModule(Customer customer,Module module);
}
