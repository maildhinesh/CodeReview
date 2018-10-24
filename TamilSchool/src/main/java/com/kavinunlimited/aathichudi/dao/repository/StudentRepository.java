package com.kavinunlimited.aathichudi.dao.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;

@RepositoryRestResource(collectionResourceRel = "students", path = "students")
public interface StudentRepository extends MongoRepository<Student, String> {
	public List<Student> findByDateOfBirth(LocalDate dateOfBirth);
	
	public List<Student> findByLastName(String lastName);
	
	public Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
}
