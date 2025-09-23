package com.jpd.model;

import java.sql.Date;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Enumerated(EnumType.STRING)
private TypeOfCourse typeOfCourse;
@OneToMany(mappedBy = "course",cascade =CascadeType.ALL )
@JsonIgnore
private List<Enrollment>enrolls;
@OneToMany(mappedBy = "course",cascade =CascadeType.ALL )
@JsonIgnore
private List<CourseTracker>customer_course;
@OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
@JsonManagedReference

private List<ModuleType>moduleType;
@Column(name = "url_img")
private String urlImg;
@ManyToOne()
@JoinColumn(name = "creator_id")
@JsonBackReference
private Creator creator;
private String description;
@Column(name = "target_audience")
private String targetAudience;

@Column(name="learning_object")
private String learningObject;
private TypeLanguage Language;
private double price;
private double revenue;

private int numberOfStudent;
@Column(name="teaching_language")
private String teachingLanguage;

private boolean ispublic;
@Column(name="learning_outcomes")
private List<String>learningOutcomes;

private List<String> requirements;
private boolean isban;
private double capacity;
@Column(name="total_rating")
private int totalRating;
private int numberModuleType;
private int numbeModule;
@Column(name = "last_update")
private Date lastUpdate;

}
