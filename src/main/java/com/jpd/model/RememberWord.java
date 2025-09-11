package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class RememberWord {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long rwId;
@JoinColumn(name = "customer_id")
@JsonIgnore
@ManyToOne
private  Customer customer;
private String word;
private String meaning;
private String description;
}
