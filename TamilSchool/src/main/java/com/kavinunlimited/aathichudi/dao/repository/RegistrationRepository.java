package com.kavinunlimited.aathichudi.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;

@RepositoryRestResource(collectionResourceRel = "registration", path = "registration")
public interface RegistrationRepository extends MongoRepository<Registration, String> {
	
	Optional<Registration> findOneByStudentAndAathichudiGrade(Student student, AathichudiGrade aathichudiGrade);
	
	List<Registration> findAllByStudent(Student student);
	
}
