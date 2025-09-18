package com.jpd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
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

@Entity
@DiscriminatorValue("PASSAGE")
@Data
public class Passage extends ModuleContent{

private String title;
private String content;

@OneToMany(mappedBy = "passage",cascade = CascadeType.ALL)
@JsonManagedReference
private List<ReadingQuestion>readingQuestions;
}
