package com.jpd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
/*
 * list<course> a =findAll()
 *for(int i=0;i<length;i++)
 *{
 *transform(course->courseDTO);
 *}
 * 
 * 
 * */
public class CourseDTO {
private long courseId;
private String name;
private String urlImg;
}
