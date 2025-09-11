package com.jpd.model;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Course {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "course_id")
private long courseId;
@NotNull
private String name;
@OneToMany(mappedBy = "course",cascade =CascadeType.ALL )
@JsonIgnore
private List<Enrollment>enrolls;
@OneToMany(mappedBy = "courseM",cascade = CascadeType.ALL)
@JsonManagedReference
private List<Module>module;
private String urlImg;
}
