package com.jpd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
/*
 * Question_Picture (
    id INT PRIMARY KEY,
    module_id INT,
    picture_url VARCHAR(255),
    FOREIGN KEY (module_id) REFERENCES Module(module_id)
)
*/
@Entity
@DiscriminatorValue("SPEAKING_PICTURE")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SpeakingPictureQuestion extends ModuleContent {
	
	private String pictureUrl;
	@OneToMany(mappedBy = "speakingPictureQuestion",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SpeakingPictureListQuestions>speakingPictureListQuestions;
	
}

