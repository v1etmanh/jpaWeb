package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Customer_ModuleType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_id")
	private long cmId;
	@ManyToOne
	@JoinColumn(name = "customer_id")  // Tạo khóa ngoại customer_id trong bảng Enrollment
	@JsonBackReference
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "moduletype_id")
	@JsonIgnore
	private ModuleType  moduleType;

	// mysql ko hỗ trợ kiểu dữ liệu làmap

	private double progress;
}
