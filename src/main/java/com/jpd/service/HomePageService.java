package com.jpd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jpd.model.Course;
import com.jpd.model.CourseDTO;
import com.jpd.repository.CourseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomePageService {
private final CourseRepository courseRe;
public List<CourseDTO>retrievedAllCourse(){
	List<Course> retrievedC= courseRe.findAll();
	
	return retrievedC.stream().map(course->new CourseDTO(course.getCourseId(),course.getName(),course.getUrlImg()))
			.collect(Collectors.toList());
}
}
