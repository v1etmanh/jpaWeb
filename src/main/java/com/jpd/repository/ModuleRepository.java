package com.jpd.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jpd.model.Course;
import com.jpd.model.Module;

public interface ModuleRepository extends CrudRepository<Module, Long>{
List<Module> findByCourseM(Course courseM);
Module findById(long id);
long countByCourseM(Course courseM);
}
