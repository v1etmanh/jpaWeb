package com.jpd.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jpd.model.CustomerFinishedModule;
import com.jpd.model.Enrollment;

public interface CustomerFinishedModuleRepository extends CrudRepository<CustomerFinishedModule, Long> {
 List<CustomerFinishedModule> findAll();
List<CustomerFinishedModule>  findByEnrollingAndModule(Enrollment enrolling,com.jpd.model.Module module);
List<CustomerFinishedModule> findByEnrolling(Enrollment enrolling);
}
