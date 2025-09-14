package com.jpd.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jpd.model.Course;
import com.jpd.model.Module;
import com.jpd.model.ModuleType;

public interface ModuleRepository extends CrudRepository<Module, Long>{
List<Module> findByModuleType(ModuleType moduleType);
Module findById(long id);

}
