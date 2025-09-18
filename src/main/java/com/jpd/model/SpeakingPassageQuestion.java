package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.DiscriminatorValue;
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
 * id INT PRIMARY KEY,
    module_id INT,
    passage TEXT,
    url_listen VARCHAR(255),
    FOREIGN KEY (module_id) REFERENCES Module(module_id)
*/
@Entity
@DiscriminatorValue("SPEAKING_PASSAGE_QUESTION")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SpeakingPassageQuestion extends ModuleContent {

	private String passageText;
	
}
