package com.jpd.model;

import java.sql.Date;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class RequestSpeaking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private long rqsId;
	   private Date nowCurrentDate;  // Đổi từ currentDate thành createdDate
	    private Date nextDate;
@OneToOne
@JoinColumn(name = "customer_id")
@JsonBackReference
private Customer customer;
private int avaiableRequest;
}
