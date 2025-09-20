package com.jpd.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CustomerTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private long transactionId;
	private String content;
	private Date createDate;
	private double total;
	private double serverGet;
	private double creatorGet;
	@OneToOne
	@JoinColumn(name = "enroll_id")
	private Enrollment enrollment;
	@PrePersist()
	public void resetCreateDate() {
	 this.createDate=Date.valueOf(LocalDate.now());
	 Course c=enrollment.getCourse();
	 Customer ct=enrollment.getCustomer();
	 this.content="course "+c.getName()+"id :"+c.getCourseId()+" by "+ct.getEmail()+"at "+ createDate.toString() ;
	}
	
}
