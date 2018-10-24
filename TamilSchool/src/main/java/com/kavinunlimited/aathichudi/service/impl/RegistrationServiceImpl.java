package com.kavinunlimited.aathichudi.service.impl;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kavinunlimited.aathichudi.dao.entity.EmergencyContact;
import com.kavinunlimited.aathichudi.dao.entity.InsuranceInfo;
import com.kavinunlimited.aathichudi.dao.entity.Parent;
import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;
import com.kavinunlimited.aathichudi.dao.entity.enums.RegistrationStatus;
import com.kavinunlimited.aathichudi.dao.repository.ParentRepository;
import com.kavinunlimited.aathichudi.dao.repository.RegistrationRepository;
import com.kavinunlimited.aathichudi.dao.repository.StudentRepository;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationRequest;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationStudent;
import com.kavinunlimited.aathichudi.exception.AathichudiException;
import com.kavinunlimited.aathichudi.exception.DBException;
import com.kavinunlimited.aathichudi.exception.DuplicateRegistrationException;
import com.kavinunlimited.aathichudi.exception.InvalidRegistrationException;
import com.kavinunlimited.aathichudi.exception.RegistrationNotFoundException;
import com.kavinunlimited.aathichudi.exception.UserNotAParentException;
import com.kavinunlimited.aathichudi.service.RegistrationService;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired RegistrationRepository registrationRepository;
	
	@Autowired StudentRepository studentRepository;
	
	@Autowired ParentRepository parentRepository;

	@Override
	public com.kavinunlimited.aathichudi.dao.entity.Registration saveRegistration(
			com.kavinunlimited.aathichudi.dao.entity.Registration registration) throws AathichudiException {
		
		Student student = registration.getStudent();
		
		Parent parentOne = student.getParents().get(0);
		parentOne = this.saveParent(parentOne);
		if(parentOne == null) {
			throw new DBException();
		}
		Parent parentTwo = student.getParents().get(1);
		parentTwo = this.saveParent(parentTwo);
		if(parentTwo == null) {
			throw new DBException();
		}
		
		student = this.saveStudent(student);
		if(student == null) {
			throw new DBException();
		}
		this.addStudent(parentOne, student);
		this.addStudent(parentTwo, student);
		
		if(registration.getId() == null) {
			registration.setId(UUID.randomUUID().toString());
		}
		registration.moveNextStatus();
		registration = this.registrationRepository.save(registration);
		if(registration == null) {
			throw new DBException();
		}
		student.setCurrentRegistration(registration);
		this.saveStudent(student);
		return registration;
	}
	
	private Student saveStudent(Student student) {
		if(student.getId() == null) {
			student.setId(UUID.randomUUID().toString());
		}
		student = this.studentRepository.save(student);
		return student;
	}
	
	private Parent saveParent(Parent parent) {
		if(parent.getId() == null) {
			parent.setId(UUID.randomUUID().toString());
		}
		parent = this.parentRepository.save(parent);
		return parent;
	}
	
	private Parent addStudent(Parent parent, Student student) {
		boolean studentFound = false;
		if(parent.getStudents() == null) {
			parent.addStudent(student);
			return parent;
		}
		for(Student addedStudent : parent.getStudents()) {
			if(student.getFirstName().equals(addedStudent.getFirstName()) && 
					student.getDateOfBirth().isEqual(addedStudent.getDateOfBirth())) {
				studentFound = true;
				break;
			}
		}
		if(!studentFound) {
			parent.addStudent(student);
		}
		return parent;
	}

	@Override
	public List<com.kavinunlimited.aathichudi.dao.entity.Registration> getAllRegistrations(){
		return this.registrationRepository.findAll();
	}

	@Override
	public com.kavinunlimited.aathichudi.dao.entity.Registration getRegistrationById(String id) throws AathichudiException {
		if(id == null || id.trim().equals("")) {
			throw new InvalidRegistrationException();
		}
		Optional<Registration> opt = this.registrationRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new RegistrationNotFoundException();
	}
	
	@Override
	public Registration createUserRegistration(CreateRegistrationRequest request) throws AathichudiException {
		// TODO Auto-generated method stub
		Parent parent = null;
		Registration registration = this.existingRegistration(request.getStudent());
		if(registration != null) {
			return registration;
		}
		Optional<Parent> optParent = this.parentRepository.findOneByEmail(request.getEmail());
		if(optParent.isPresent()) {
			parent = optParent.get();
		}
		if(parent != null) {
			List<Student> students = parent.getStudents();
			if(students.size() > 0) {
				boolean studentFound = false;
				Student foundStudent = null;
				for(Student student: students) {
					if(student.getFirstName().equals(request.getStudent().getFirstName()) 
							&& student.getLastName().equals(request.getLastName())) {	
						studentFound = true;
						if(student.getCurrentRegistration().getAathichudiGrade().equals(request.getStudent().getAathichudiGrade())) {
							throw new DuplicateRegistrationException();
						}
						foundStudent = student;
					}
				}
				if(studentFound) {
					return createRegistration(foundStudent, request.getStudent().getAathichudiGrade());
				}
			}
			Student student = this.createStudent(request.getStudent(), parent);
			return this.createRegistration(student, request.getStudent().getAathichudiGrade());
		}
		parent = this.createParent(request);
		Student student = this.createStudent(request.getStudent(), parent);
		parent.addStudent(student);
		this.parentRepository.save(parent);
		return this.createRegistration(student, request.getStudent().getAathichudiGrade());
	}
	
	private Registration existingRegistration(CreateRegistrationStudent studentRequest) {
		Optional<Student> opt = this.studentRepository.findByFirstNameAndLastName(studentRequest.getFirstName(), studentRequest.getLastName());
		if(!opt.isPresent()) {
			return null;
		}
		Student student = opt.get();
		Optional<Registration> registration = this.registrationRepository.findOneByStudentAndAathichudiGrade(student, studentRequest.getAathichudiGrade());
		if(registration.isPresent()) {
			return registration.get();
		}
		return null;
	}
	
	private Parent createParent(CreateRegistrationRequest request) {
		Parent parent = new Parent()
						.setId(UUID.randomUUID().toString())
						.setEmail(request.getEmail())
						.setFirstName(request.getFirstName())
						.setPrimary(true)
						.setLastName(request.getLastName());
		this.parentRepository.save(parent);
		return parent;
	}
	
	private Student createStudent(CreateRegistrationStudent requestStudent, Parent parent) {
		Student student = new Student()
							.setId(UUID.randomUUID().toString())
							.setFirstName(requestStudent.getFirstName())
							.setLastName(requestStudent.getLastName())
							.addParent(parent);
		this.studentRepository.save(student);
		return student;

	}
	
	private Registration createRegistration(Student student, AathichudiGrade grade) {
		
		Registration registration = new Registration();
		registration
			.setId(UUID.randomUUID().toString())
			.setStudent(student)
			.setAathichudiGrade(grade)
			.setStatus(RegistrationStatus.PENDING_USER);
		
		this.registrationRepository.save(registration);
		return registration;
	}

	@Override
	public Registration validateRegistrationLink(String registrationId, String email) throws AathichudiException {
		Optional<Registration> opt = this.registrationRepository.findById(registrationId);
		if(!opt.isPresent()) {
			throw new RegistrationNotFoundException();
		}
		Registration registration = opt.get();
		String parentEmail = "";
		for(Parent parent : registration.getStudent().getParents()) {
			if(parent.isPrimary()){
				parentEmail = parent.getEmail();
			}
		}
		if(parentEmail.isEmpty()) {
			throw new InvalidRegistrationException();
		}
		if(parentEmail.equals(email)) {
			return registration;
		}
		throw new InvalidRegistrationException();
	}

	@Override
	public List<Registration> getAllRegistrationsByUser(User user) throws AathichudiException {
		// TODO Auto-generated method stub
		List<Registration> registrations = new ArrayList<Registration>();
		if(user.getParent() == null) {
			throw new UserNotAParentException();
		}
		if(user.getParent().getStudents() == null) {
			return registrations;
		}
		for(Student student: user.getParent().getStudents()) {
			List<Registration> studentRegistrations = this.registrationRepository.findAllByStudent(student);
			registrations.addAll(studentRegistrations);
		}
		return registrations;
	}

	@Override
	public Registration getRegistrationByParent(User user, String id) throws AathichudiException {
		List<Registration> registrations = this.getAllRegistrationsByUser(user);
		for(Registration registration: registrations) {
			if(registration.getId().equals(id)) {
				return registration;
			}
		}
		throw new RegistrationNotFoundException();
	}

}
