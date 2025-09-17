package com.jpd.model;

import java.sql.Date;
import java.util.List;


import org.hibernate.annotations.Collate;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Data
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="customer_id")
private long customerId;
private Date createDate;
private String username;
@Column(unique = true)
private String email;
private String givenName;
private String familyName;
private String role;

@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
@JsonManagedReference
private List<Enrollment>enrollments;
@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
@JsonManagedReference
private List<RememberWord>rememberWords;
@Column(name = "current_words")
private int currentWords;
private int numberRequest;
@OneToOne(mappedBy = "customer")
@JsonManagedReference
private RequestSpeaking requestSpeaking;
@OneToOne(mappedBy = "customer")
@JsonManagedReference
private Creator creator;
@Column(name="number_enrollment")
private int numberEnrollment;
@Column(name = "num")
private int numberFinishCourse;
}
