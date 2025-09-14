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
 * Passage (
    id INT PRIMARY KEY,
    module_id INT,
    content LONGTEXT,
    FOREIGN KEY (module_id) REFERENCES Module(module_id)
)
*/
@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Passage {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "passage_id")
private long passageId;
private String title;
private String content;
@ManyToOne
@JoinColumn(name = "module_id")
@JsonBackReference
private Module module;
@OneToMany(mappedBy = "passage",cascade = CascadeType.ALL)
@JsonManagedReference
private List<ReadingQuestion>readingQuestions;
}
