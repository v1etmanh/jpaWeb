package com.jpd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpd.dto.CourseDTO;
import com.jpd.model.Course;
import com.jpd.model.Customer;
import com.jpd.service.HomePageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/homepage")

public class HomeController {
	@Autowired
private HomePageService homePageService;
@GetMapping("/courses")
public ResponseEntity< List<CourseDTO> >getAllCourse() {
	try {
		List<CourseDTO>result=this.homePageService.retrievedAllCourse();
		return ResponseEntity.ok(result);
	}catch (Exception e) {
		// TODO: handle exception
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}

}
