package com.kavinunlimited.aathichudi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kavinunlimited.aathichudi.UnitTestDataEngine;
import com.kavinunlimited.aathichudi.dao.entity.Parent;
import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;
import com.kavinunlimited.aathichudi.dao.entity.enums.RegistrationStatus;
import com.kavinunlimited.aathichudi.dao.entity.enums.SchoolGrade;
import com.kavinunlimited.aathichudi.dao.repository.ParentRepository;
import com.kavinunlimited.aathichudi.dao.repository.RegistrationRepository;
import com.kavinunlimited.aathichudi.dao.repository.StudentRepository;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationRequest;
import com.kavinunlimited.aathichudi.domain.request.CreateRegistrationStudent;
import com.kavinunlimited.aathichudi.exception.InvalidRegistrationException;
import com.kavinunlimited.aathichudi.exception.RegistrationNotFoundException;
import com.kavinunlimited.aathichudi.exception.UserNotAParentException;
import com.kavinunlimited.aathichudi.service.impl.RegistrationServiceImpl;

@RunWith(SpringRunner.class)
public class RegistrationServiceTest {
	
	@TestConfiguration
	static class RegistrationServiceTestConfiguration {
		@Bean
		public RegistrationService registrationService() {
			return new RegistrationServiceImpl();
		}
		@Bean
		public UnitTestDataEngine testDataEngine() {
			return new UnitTestDataEngine();
		}
	}
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private UnitTestDataEngine testDataEngine;
	
	@MockBean
	private RegistrationRepository registrationRepository;
	
	@MockBean
	private ParentRepository parentRepository;
	
	@MockBean
	private StudentRepository studentRepository;
	
	private final String TEST_DATA_PREFIX = "testdata/unit/registrationservice/";
	
	private Student mukilan;
	
	private Student akilan;
	
	private Student nithila;
	
	private User parentUserOne;
	
	private User parentUserTwo;
	
	private User nonParentUser;
	
	private List<Registration> mukilanRegistrations = new ArrayList<Registration>();
	
	private List<Registration> akilanRegistrations = new ArrayList<Registration>();
	
	private List<Registration> nithilaRegistrations = new ArrayList<Registration>();
	
	@Before
	public void setup() {
		try {
			mukilan = new Student().setFirstName("Mukilan").setLastName("Dhinesh");
			akilan = new Student().setFirstName("Akilan").setLastName("Dhinesh");
			nithila = new Student().setFirstName("Nithila").setLastName("Bharathi");
			parentUserOne = new User().setFirstName("Dhinesh").setLastName("Kumararaman").setEmail("parentone@gmail.com");
			parentUserTwo = new User().setFirstName("Bharathirajan").setLastName("Damodaran").setEmail("parenttwo@test.com");
			
			Parent dhinesh = new Parent().setFirstName("Dhinesh").setLastName("Kumararaman").setEmail("parentone@gmail.com");
			dhinesh.addStudent(mukilan).addStudent(akilan);
			dhinesh.setPrimary(true);
			mukilan.addParent(dhinesh);
			akilan.addParent(dhinesh);
			parentUserOne.setParent(dhinesh);
			
			Parent bharathi = new Parent().setFirstName("Bharathirajan").setLastName("Damodaran").setEmail("parenttwo@test.com");
			bharathi.addStudent(nithila);
			bharathi.setPrimary(true);
			nithila.addParent(bharathi);
			parentUserTwo.setParent(bharathi);
			
			Registration mukilanNilai1 = new Registration().setId("REGISTRATION1")
															.setStudent(mukilan)
															.setStatus(RegistrationStatus.COMPLETE)
															.setAathichudiGrade(AathichudiGrade.NILAI_1)
															.setSchoolGrade(SchoolGrade.GRADE_3);
			Registration mukilanNilai2 = new Registration().setId("REGISTRATION2")
															.setStudent(mukilan)
															.setStatus(RegistrationStatus.PENDING_USER)
															.setAathichudiGrade(AathichudiGrade.NILAI_2)
															.setSchoolGrade(SchoolGrade.GRADE_4);
			mukilanRegistrations.add(mukilanNilai1);
			mukilanRegistrations.add(mukilanNilai2);
			
			Registration akilanMazhalai = new Registration().setId("REGISTRATION3")
															.setStudent(akilan)
															.setStatus(RegistrationStatus.COMPLETE)
															.setAathichudiGrade(AathichudiGrade.MAZHALAI)
															.setSchoolGrade(SchoolGrade.GRADE_1);
			akilanRegistrations.add(akilanMazhalai);
			
			Registration nithilaNilai1 = new Registration().setId("REGISTRATION4")
															.setStudent(nithila)
															.setStatus(RegistrationStatus.COMPLETE)
															.setAathichudiGrade(AathichudiGrade.NILAI_1)
															.setSchoolGrade(SchoolGrade.GRADE_1);
			nithilaRegistrations.add(nithilaNilai1);
			Registration nithilaNila2 = new Registration().setId("REGISTRATION5")
															.setStudent(nithila)
															.setStatus(RegistrationStatus.PENDING_USER)
															.setAathichudiGrade(AathichudiGrade.NILAI_2)
															.setSchoolGrade(SchoolGrade.GRADE_2);

			nithilaRegistrations.add(nithilaNila2);
			List<Registration> allRegistrations = new ArrayList<Registration>();
			allRegistrations.addAll(mukilanRegistrations);
			allRegistrations.addAll(akilanRegistrations);
			allRegistrations.addAll(nithilaRegistrations);
			
			Mockito.when(registrationRepository.findAll()).thenReturn(allRegistrations);
			Mockito.when(registrationRepository.findAllByStudent(mukilan)).thenReturn(mukilanRegistrations);
			Mockito.when(registrationRepository.findAllByStudent(akilan)).thenReturn(akilanRegistrations);
			Mockito.when(registrationRepository.findAllByStudent(nithila)).thenReturn(nithilaRegistrations);
			Mockito.when(registrationRepository.findById(mukilanNilai1.getId())).thenReturn(Optional.of(mukilanNilai1));
			
			nonParentUser = new User().setFirstName("Invalid").setLastName("Parent").setEmail("test@test.com").setParent(null);
			
			Mockito.when(studentRepository.findByFirstNameAndLastName(mukilan.getFirstName(), mukilan.getLastName())).thenReturn(Optional.of(mukilan));
			Mockito.when(registrationRepository.findOneByStudentAndAathichudiGrade(mukilan, AathichudiGrade.NILAI_2)).thenReturn(Optional.of(mukilanRegistrations.get(1)));
		}catch(Exception ex) {
			System.err.println(ex.getLocalizedMessage());
		}
	}
	//UT001
	@Test
	public void whenGetAllRegistrations_returnFiveRegistrations() {
		List<Registration> registrations = this.registrationService.getAllRegistrations();
		assertThat(registrations).hasSize(5);
	}
	//UT002
	@Test
	public void whenGetRegistrationsByUser_returnAppropriateNumber() {
		List<Registration> registrations = this.registrationService.getAllRegistrationsByUser(parentUserOne);
		assertThat(registrations).hasSize(3);
		List<Registration> registrationParentTwo = this.registrationService.getAllRegistrationsByUser(parentUserTwo);
		assertThat(registrationParentTwo).hasSize(2);
	}
	//UT003
	@Test(expected=UserNotAParentException.class)
	public void whenGetRegistrationByNonParent_throwsUserNotAParentException() {
		List<Registration> registration = this.registrationService.getAllRegistrationsByUser(nonParentUser);
	}
	
	//UT004
	@Test
	public void whenGetRegistrationById_returnsRegistration() {
		Registration registration = this.registrationService.getRegistrationById("REGISTRATION1");
		assertThat(registration).isNotNull();
		assertThat(registration.getAathichudiGrade()).isEqualTo(AathichudiGrade.NILAI_1);
		assertThat(registration.getStudent()).isEqualTo(mukilan);
	}
	
	//UT005
	@Test(expected = InvalidRegistrationException.class)
	public void whenRegistrationIdEmpty_throwsInvalidRegistrationException() {
		Registration registration = this.registrationService.getRegistrationById("");
	}
	
	//UT006
	@Test(expected = RegistrationNotFoundException.class)
	public void whenRegistrationIdIsInvalid_throwRegistrationNotFoundException() {
		this.registrationService.getRegistrationById("NOREGISTRATION");
	}
	
	//UT007
	@Test
	public void whenValidRegistration_thenReturnRegistration() {
		Registration registration = this.registrationService.validateRegistrationLink("REGISTRATION1", parentUserOne.getEmail());
		assertThat(registration).isNotNull();
		assertThat(registration.getAathichudiGrade()).isEqualTo(AathichudiGrade.NILAI_1);
		assertThat(registration.getStudent()).isEqualTo(mukilan);
	}
	
	//UT008
	@Test(expected = RegistrationNotFoundException.class)
	public void whenInvalidRegistration_thenThrowRegistrationNotFoundException() {
		Registration registration = this.registrationService.validateRegistrationLink("INVALIDREGISTRATION", parentUserOne.getEmail());
	}
	
	//UT009
	@Test(expected = InvalidRegistrationException.class)
	public void whenInvalidEmail_thenThrowInvalidRegistrationException() {
		Registration registration = this.registrationService.validateRegistrationLink("REGISTRATION1", "invalid@test.com");
	}
	
	//UT010
	@Test
	public void whenGetRegistrationByParentValidParent_returnRegistration() {
		Registration registration = this.registrationService.getRegistrationByParent(parentUserTwo, "REGISTRATION4");
		assertThat(registration).isNotNull();
		assertThat(registration.getAathichudiGrade()).isEqualTo(AathichudiGrade.NILAI_1);
		assertThat(registration.getStudent()).isEqualTo(nithila);
	}
	
	//UT011
	@Test(expected=RegistrationNotFoundException.class)
	public void whenGetRegistrationByParentInvalidParent_throwRegistrationNotFoundException() {
		this.registrationService.getRegistrationByParent(parentUserTwo, "REGISTRATION1");
	}
	
	//UT012
	@Test
	public void whenValidCreateRegistrationRequest_createRegistration() {
		CreateRegistrationRequest request = new CreateRegistrationRequest();
		request.setEmail("newparent@test.com");
		request.setFirstName("New");
		request.setLastName("Parent");
		CreateRegistrationStudent student = new CreateRegistrationStudent();
		student.setFirstName("New");
		student.setLastName("Student");
		student.setAathichudiGrade(AathichudiGrade.NILAI_1);
		request.setStudent(student);
		
		Registration registration = this.registrationService.createUserRegistration(request);
		assertThat(registration).isNotNull();
		assertThat(registration.getAathichudiGrade()).isEqualTo(student.getAathichudiGrade());
		assertThat(registration.getStudent().getFirstName()).isEqualTo(student.getFirstName());
		assertThat(registration.getStudent().getParents().get(0).getEmail()).isEqualTo(request.getEmail());
	}
	
	//UT013
	@Test
	public void whenExistingCreateRegistrationRequest_thenReturnExistingRegistration() {
		CreateRegistrationRequest request = new CreateRegistrationRequest();
		request.setEmail("parentone@test.com");
		request.setFirstName("Dhinesh");
		request.setLastName("Kumararaman");
		CreateRegistrationStudent student = new CreateRegistrationStudent();
		student.setFirstName("Mukilan");
		student.setLastName("Dhinesh");
		student.setAathichudiGrade(AathichudiGrade.NILAI_2);
		request.setStudent(student);
		
		Registration registration = this.registrationService.createUserRegistration(request);
		assertThat(registration).isNotNull();
		assertThat(registration).isEqualTo(mukilanRegistrations.get(1));
		assertThat(registration.getStudent().getFirstName()).isEqualTo(mukilan.getFirstName());
		assertThat(registration.getStudent().getParents().get(0).getEmail()).isEqualTo(parentUserOne.getEmail());
	}

}
