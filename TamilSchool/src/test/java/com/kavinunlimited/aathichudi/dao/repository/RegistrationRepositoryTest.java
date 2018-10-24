package com.kavinunlimited.aathichudi.dao.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RegistrationRepositoryTest {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Test
	public void whenFindOneByStudentAndAathichudiGrade_thenReturnRegistration() {
		Student mukilan = new Student().setFirstName("Mukilan").setLastName("Dhinesh");
		this.mongoTemplate.save(mukilan);
		Registration registration = new Registration()
									.setStudent(mukilan)
									.setAathichudiGrade(AathichudiGrade.NILAI_2);
		
		this.mongoTemplate.save(registration);
		
		Optional<Registration> found = this.registrationRepository.findOneByStudentAndAathichudiGrade(mukilan, registration.getAathichudiGrade());
		
		assertThat(found.isPresent() && found.get().getAathichudiGrade().equals(registration.getAathichudiGrade()) && found.get().getStudent().getFirstName().equals(mukilan.getFirstName()));
	}
	
	@Test
	public void whenFindAllByStudent_thenReturnRegistrations() {
		Student mukilan = new Student().setFirstName("Mukilan").setLastName("Dhinesh");
		this.mongoTemplate.save(mukilan);
		Registration registrationNilai1 = new Registration()
										.setStudent(mukilan)
										.setAathichudiGrade(AathichudiGrade.NILAI_1);
		this.mongoTemplate.save(registrationNilai1);
		Registration registrationNilai2 = new Registration().setAathichudiGrade(AathichudiGrade.NILAI_2).setStudent(mukilan);
		this.mongoTemplate.save(registrationNilai2);
		
		List<Registration> registrations = this.registrationRepository.findAllByStudent(mukilan);
		assertThat(registrations.size() == 2 && 
				registrations.get(0).getStudent().getFirstName().equals(mukilan.getFirstName()) &&
				(registrations.get(0).getAathichudiGrade().equals(registrationNilai1.getAathichudiGrade()) || registrations.get(0).getAathichudiGrade().equals(registrationNilai2.getAathichudiGrade())) &&
				(registrations.get(1).getAathichudiGrade().equals(registrationNilai1.getAathichudiGrade()) || registrations.get(1).getAathichudiGrade().equals(registrationNilai2.getAathichudiGrade()))		
				);
	}
}
