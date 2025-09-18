package com.jpd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
/*
 * Reading_Question (
    id INT PRIMARY KEY,
    passage_id INT,
    question TEXT,
    FOREIGN KEY (passage_id) REFERENCES Passage(id)
)
*/
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ReadingQuestion {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "rq_id")
private long rqId;
@ManyToOne
@JoinColumn(name = "id")
@JsonBackReference
private Passage passage;
private String question ;
@OneToMany(mappedBy = "readingQuestion",cascade = CascadeType.ALL)
@JsonManagedReference
private List<ReadingQuestionOptions>readingQuestionOptions;
private String feedBack;
}
