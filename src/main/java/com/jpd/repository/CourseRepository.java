package com.jpd.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpd.model.Course;
@Repository
public interface CourseRepository extends CrudRepository<Course,Long>{
List<Course>findAll();
Course findByCourseId(long courseId);

}
