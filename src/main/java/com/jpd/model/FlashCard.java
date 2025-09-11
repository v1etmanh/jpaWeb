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

/*
 *Flashcard (
    id INT PRIMARY KEY,
    module_id INT,
    word VARCHAR(100),
    meaning TEXT,
    audio_url VARCHAR(255),
    FOREIGN KEY (module_id) REFERENCES Module(module_id)
)
*/
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class FlashCard {
	@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "flash_card_id")
private long flashCardId;
@ManyToOne
@JoinColumn(name = "module_id")
@JsonBackReference
private Module module;
private String word;
private String meaning;


}
