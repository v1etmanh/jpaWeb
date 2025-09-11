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
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ReadingQuestionOptions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "option_id")
	private long optionId;
	@ManyToOne
	@JoinColumn(name="rq_id")
	@JsonBackReference
	private ReadingQuestion readingQuestion;

	private String optionText;
	private boolean isCorrect;
}
