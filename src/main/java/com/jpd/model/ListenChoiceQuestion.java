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
@Entity
@DiscriminatorValue("LISTEN_CHOICE")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ListenChoiceQuestion extends ModuleContent{

	private String question;
	private String url;
	
	@OneToMany(mappedBy = "listionChoiceQuestion",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ListionChoiceOptions>listionChoiceOptions;
}
