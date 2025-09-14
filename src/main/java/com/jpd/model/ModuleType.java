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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ModuleType {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "moduletype_id")
private long moduletypeId;
@Column(unique = true)
private String moduleName;
@OneToMany(mappedBy = "moduleType",cascade = CascadeType.ALL)
@JsonManagedReference
private List<Module> module;
@ManyToOne
@JoinColumn(name = "course_id")
@JsonBackReference
private Course course;
}
