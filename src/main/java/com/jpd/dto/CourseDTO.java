package com.jpd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor

public class CourseDTO {
private long courseId;
private String name;
private String urlImg;
}
