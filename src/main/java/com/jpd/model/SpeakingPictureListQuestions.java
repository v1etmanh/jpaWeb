package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
/*
 * Listen_Answer_Picture (
    id INT PRIMARY KEY,
   
    question TEXT,
    answer_text TEXT,
    audio_url VARCHAR(255),
    FOREIGN KEY (question_picture_id) REFERENCES Question_Picture(id)
)
*/
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class SpeakingPictureListQuestions {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long qid;
private String question;

private String answer;
@ManyToOne
@JoinColumn(name = "spq_id")
@JsonBackReference
private SpeakingPictureQuestion speakingPictureQuestion;
}
