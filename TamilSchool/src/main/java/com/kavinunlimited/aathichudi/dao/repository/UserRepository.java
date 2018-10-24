package com.kavinunlimited.aathichudi.dao.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kavinunlimited.aathichudi.dao.entity.User;

@RepositoryRestResource(collectionResourceRel = "guests", path = "users")
public interface UserRepository extends MongoRepository<User, String> {
	public Optional<User> findOneByEmail(String email);
}
