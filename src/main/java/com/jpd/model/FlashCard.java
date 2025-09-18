package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
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
@DiscriminatorValue("FLASHCARD")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FlashCard extends ModuleContent {
    private String word;
    private String meaning;
    @Column(name="img_url")
    private String imgUrl;
}

