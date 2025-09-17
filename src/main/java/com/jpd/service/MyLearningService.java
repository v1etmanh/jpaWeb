package com.jpd.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpd.dto.CourseDTO;
import com.jpd.model.Course;
import com.jpd.model.Customer;
import com.jpd.model.CustomerFinishedModule;
import com.jpd.model.Enrollment;
import com.jpd.model.RequestSpeaking;
import com.jpd.repository.CourseRepository;
import com.jpd.repository.CustomerRepository;
import com.jpd.repository.EnrollingRepository;
import com.jpd.repository.RequestSpeakingRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyLearningService {
private final EnrollingRepository enrollRe;
private final CustomerRepository cusRe;
private final CourseRepository courseRe;
private final RequestSpeakingRepository rqRe;
public Customer retrieveCustomerByEmail(String email) {
	return cusRe.findCustomerByEmail(email);
}

public void EnrollNewCustomer(Customer cus )
{ Course course=this.courseRe.findByCourseId(1);
Enrollment e=new Enrollment();
        e.setCourse(course);
        e.setCustomer(cus);
       e.setProgress(0.0);
       e.setCreateDate(new Date()); // Hoặc dùng LocalDateTime.now()
	this.enrollRe.save(e);
	  RequestSpeaking rq = new RequestSpeaking();
	    rq.setAvaiableRequest(6);
	    rq.setCustomer(cus);
	    
	    // Sửa cách tạo date
	    Date today = new Date();
	    rq.setNowCurrentDate(new java.sql.Date(today.getTime()));
	    
	    // Tạo ngày mai (thêm 1 ngày)
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(today);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    rq.setNextDate(new java.sql.Date(cal.getTimeInMillis()));
	    
	    // Lưu RequestSpeaking
	    this.rqRe.save(rq);
	}
public List<CourseDTO>retrievedCourseByCustomer(Customer customer){
	List<Enrollment>enrolls= this.enrollRe.findByCustomer(customer);
	List<Course>Result=new ArrayList<>();
	
	for(int i=0;i<enrolls.size();i++)
	{
		Result.add(enrolls.get(i).getCourse());
	}
	return Result.stream().map(course->new CourseDTO(course.getCourseId(),course.getName(),course.getUrlImg()))
			.collect(Collectors.toList());
}
public Customer SavenewCus(Customer customer) {
	return this.cusRe.save(customer);
}

}
