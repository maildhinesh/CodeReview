package com.kavinunlimited.aathichudi.dao.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kavinunlimited.aathichudi.dao.entity.Parent;

@RepositoryRestResource(collectionResourceRel = "parents", path = "parents")
public interface ParentRepository extends MongoRepository<Parent, String> {
	
	public Optional<Parent> findOneByEmail(String email);

}
