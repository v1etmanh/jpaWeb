package com.jpd.repository;

import org.springframework.data.repository.CrudRepository;

import com.jpd.model.Enrollment;
import com.jpd.model.Customer;
import java.util.List;
import java.util.Optional;

import com.jpd.model.Course;


public interface EnrollingRepository extends CrudRepository<Enrollment, Long> {
 Enrollment findByCustomerAndCourse(Customer customer, Course course);
 Enrollment findByEnrollId(long enrollId);
 List<Enrollment> findByCustomer(Customer customer);
}