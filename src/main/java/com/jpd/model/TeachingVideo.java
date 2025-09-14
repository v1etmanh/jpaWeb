package com.jpd.model;

import java.sql.Date;

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

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TeachingVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teaching_video_id")
	private long teachingVideoID;
	private String url;
	private double  capacity;
	@Column(name = "title_video")
	private String titleVideo;
	private double duration;
	@ManyToOne
	@JoinColumn(name = "module_id")
	@JsonBackReference
	private Module module;
	
}
