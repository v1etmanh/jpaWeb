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
/*Multiple_Choice_Option (
    id INT PRIMARY KEY,
    question_id INT,
    option_label CHAR(1),   -- A, B, C, D
    content TEXT,
    is_correct BOOLEAN,
    FOREIGN KEY (question_id) REFERENCES Multiple_Choice_Question(id)
)
*/
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MutipleChoiceOptions {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private long optionId;
@ManyToOne
@JoinColumn(name="mcq_id")
@JsonBackReference
private MultipleChoiceQuestion multipleChoiceQuestion;

private String optionText;
private boolean isCorrect;
}
