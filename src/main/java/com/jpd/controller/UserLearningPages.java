package com.jpd.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpd.model.Course;
import com.jpd.model.CourseDTO;
import com.jpd.model.Customer;
import com.jpd.repository.CustomerRepository;
import com.jpd.service.MyLearningService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class UserLearningPages {
private final	MyLearningService myLearningService;

	@GetMapping()
	    public ResponseEntity< Customer> getUserDetailsAfterLogin(@AuthenticationPrincipal Jwt jwt) {
	        String email=jwt.getClaimAsString("email");
	        String name =jwt.getClaimAsString("name");
	        String givenName=jwt.getClaimAsString("given_name");
	        String familyName=jwt.getClaimAsString("family_name");
	        Customer c=myLearningService.retrieveCustomerByEmail(email);
	        if(c==null) {
	        	c=new Customer();
	        c.setUsername(name);
	        c.setEmail(email);  
	        c.setRole("USER");
	        c.setCreateDate(new Date(System.currentTimeMillis()));
	        c.setFamilyName(familyName);
	        c.setGivenName(givenName);
	        c.setLimitWord(0);
	       Customer c1= myLearningService.SavenewCus(c);
	       this.myLearningService.EnrollNewCustomer(c1);
	        }
	        return ResponseEntity.ok(c);
	    }
	 
	@GetMapping("/myCourses")
	public ResponseEntity <List<CourseDTO> >getCourseOfUser( @AuthenticationPrincipal Jwt jwt) {
		try {String email=jwt.getClaimAsString("email");
		 Customer c=myLearningService.retrieveCustomerByEmail(email);
		 if(c==null) {
			 return ResponseEntity.notFound().build();
		 }
		List<CourseDTO>result=  this.myLearningService.retrievedCourseByCustomer(c);
		return ResponseEntity.ok(result);
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
