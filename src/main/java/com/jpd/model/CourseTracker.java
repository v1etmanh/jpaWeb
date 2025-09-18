package com.jpd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CourseTracker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
	@ManyToOne
	@JoinColumn(name = "customer_id")  // Tạo khóa ngoại customer_id trong bảng Enrollment
	@JsonBackReference
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "course_id")  // Tạo khóa ngoại course_id trong bảng Enrollment
	private Course course;
	private double progress;
    
	

}
