package com.jpd.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Data
public class Creator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="creator_id")
	private long creatorId;
	@OneToMany(mappedBy ="creator",cascade = CascadeType.ALL )
	@JsonManagedReference
	private List<Course>courses;
	@OneToOne
	@JoinColumn(name = "customer_id")
	@JsonBackReference
	
	private Customer customer;
	@Column(name = "full_name")
	//validation
	private String fullName;
	//validation
	private String mobiNumber;
	//validation
	
	//validation
	@Column(name = "title_self")
	private String titleSelf;
	@Column(name = "image_url")
	private String imageUrl;
	@Column(name="payment_email")
	private String  paypalEmail;
	private double currentCapacity;
	private double maxCapacity;
	private double balance;
	@OneToMany(mappedBy = "creator",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Withdrawals> withdrawals;
	@OneToMany(mappedBy = "creator",cascade =CascadeType.ALL )
	@JsonManagedReference
	private List<UserSubscription>userSubscriptions;
	@Column(name="number_courses")
	private int numberCourses;
	@Column(name = "total_student")
	private int totalStudent;
	
}
