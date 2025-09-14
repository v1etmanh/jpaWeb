package com.jpd.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Withdrawals {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "widthdrawals_id")
	private long widthdrawalsId;
	@ManyToOne
	@JoinColumn(name = "creator_id")
	@JsonBackReference
	private Creator creator;
	private double amount;
	@Column(name = "create_date")
	private Date createDate;
	private String content;
}
