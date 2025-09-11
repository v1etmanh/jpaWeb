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
 * id INT PRIMARY KEY,
    module_id INT,
    passage TEXT,
    url_listen VARCHAR(255),
    FOREIGN KEY (module_id) REFERENCES Module(module_id)
*/
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class SpeakingPassageQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private long spqId;
	@ManyToOne()
	@JoinColumn(name = "module_id")
	@JsonBackReference
	private Module module;
	private String passageText;
	
}
