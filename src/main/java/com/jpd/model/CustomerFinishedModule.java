package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class CustomerFinishedModule {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long finishId;
@ManyToOne()
@JoinColumn(name = "enroll_id")
@JsonIgnore
private Enrollment enrolling;
@ManyToOne
@JoinColumn(name = "module_id")
@JsonIgnore
private Module  module;
}
