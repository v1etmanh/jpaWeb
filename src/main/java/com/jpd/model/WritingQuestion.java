package com.jpd.model;

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
public class WritingQuestion {
	@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "writing_id")
private long writingId;
@ManyToOne
@JoinColumn(name = "module_id")
@JsonBackReference
private Module module;
private String question;
@Column(name = "url_image")
private String urlImage;
@Column(name = "user_answer")
private String userAnswer;
private String feedback;
private double mark;

}
