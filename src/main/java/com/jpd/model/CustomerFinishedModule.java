package com.jpd.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerFinishedModule {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "finish_id")
private long finishId;
@ManyToOne
@JoinColumn(name = "customer_id")  // Tạo khóa ngoại customer_id trong bảng Enrollment
@JsonBackReference
private Customer customer;
@ManyToOne
@JoinColumn(name = "module_id")
@JsonIgnore
private Module  module;

// mysql ko hỗ trợ kiểu dữ liệu làmap

private double progress;
}
