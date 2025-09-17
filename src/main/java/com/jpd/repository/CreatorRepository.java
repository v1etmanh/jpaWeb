package com.jpd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpd.model.Creator;

@Repository
public interface CreatorRepository extends CrudRepository<Creator, Long> {

}
