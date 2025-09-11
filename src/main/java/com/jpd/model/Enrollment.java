package com.jpd.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Enrollment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "enroll_id")
private long enrollId;
@ManyToOne
@JoinColumn(name = "customer_id")  // Tạo khóa ngoại customer_id trong bảng Enrollment
@JsonBackReference
private Customer customer;
private Date createDate;
@ManyToOne
@JoinColumn(name = "course_id")  // Tạo khóa ngoại course_id trong bảng Enrollment
private Course course;
@OneToMany(mappedBy = "enrolling",cascade = CascadeType.ALL)
@JsonIgnore
private List<CustomerFinishedModule>customerFinishedModules;
private double progress;
}
