package com.jpd.model;

import org.hibernate.internal.build.AllowNonPortable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Customer_Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
private long cqId;
	@ManyToOne
	@JoinColumn(name = "id")
	@JsonBackReference
private ModuleContent moduleContent;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	private int limitRequest;
	@PrePersist()
	public void setLimitRequestD() {
		if(moduleContent.getTypeOfContent()==TypeOfContent.SPEAKING_PICTURE||
				moduleContent.getTypeOfContent()==TypeOfContent.SPEAKING_PASSAGE||
				moduleContent.getTypeOfContent()==TypeOfContent.WRITING)
		{
			this.limitRequest=5;
		}
		else limitRequest=Integer.MAX_VALUE;
		
	}
}
