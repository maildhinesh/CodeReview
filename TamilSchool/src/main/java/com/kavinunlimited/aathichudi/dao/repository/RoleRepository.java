package com.kavinunlimited.aathichudi.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kavinunlimited.aathichudi.dao.entity.Role;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends MongoRepository<Role, String> {
	
	Role findOneByRole(String role);
}
