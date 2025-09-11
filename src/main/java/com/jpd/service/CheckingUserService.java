package com.jpd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpd.model.Course;
import com.jpd.model.Customer;
import com.jpd.model.Enrollment;
import com.jpd.repository.CourseRepository;
import com.jpd.repository.CustomerRepository;
import com.jpd.repository.EnrollingRepository;


@Service
public class CheckingUserService {
@Autowired
private CustomerRepository customerRepository;
@Autowired 
CourseRepository courseRepository;
@Autowired
private EnrollingRepository enrollmentRepository;

public Enrollment checkIsEnroll(String email,long courseId) {
	Customer customer=this.customerRepository.findCustomerByEmail(email);
	Course course=this.courseRepository.findByCourseId(courseId);
	if(course==null||customer==null) {return null;}
	Enrollment en=this.enrollmentRepository.findByCustomerAndCourse(customer, course);
	if(en==null)return null;
	return en;
	
	}
}
