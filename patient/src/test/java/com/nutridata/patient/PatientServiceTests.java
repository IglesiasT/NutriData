package com.nutridata.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTests {
	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private PatientService patientService;

	private Patient existingPatient;
	private PatientDTO patientDTO;
	private final Long patientId = 1L;

	@BeforeEach
	void setUp() {
		// Setup existing patient
		existingPatient = new Patient();
		existingPatient.setId(patientId);
		existingPatient.setName("Jane");
		existingPatient.setSurname("Smith");
		existingPatient.setEmail("jane.smith@example.com");
		existingPatient.setAge(30);
		existingPatient.setWeight(65.5);
		existingPatient.setHeight(165.0);

		// Setup DTO with updated values
		patientDTO = new PatientDTO();
		patientDTO.setName("Jane");
		patientDTO.setSurname("Doe");
		patientDTO.setEmail("jane.doe@example.com");
		patientDTO.setAge(31);
		patientDTO.setWeight(64.0);
		patientDTO.setHeight(165.0);
	}

	@Test
	public void getPatients_ShouldReturnAllPatients() {
		List<Patient> patients = List.of(new Patient());
		when(this.patientRepository.findAll()).thenReturn(patients);

		List<Patient> result = patientService.getPatients();

		assertEquals(patients, result);
	}

	@Test
	public void getPatients_WithNoPatients_ShouldReturnEmptyList() {
		List<Patient> patients = List.of();
		when(this.patientRepository.findAll()).thenReturn(Collections.emptyList());

		List<Patient> result = patientService.getPatients();

		assertEquals(patients, result);
	}

	@Test
	public void getPatient_WithValidId_ShouldReturnPatient() {
		Patient patient = new Patient();
		when(this.patientRepository.findById(1L)).thenReturn(Optional.of(patient));

		Patient result = patientService.getPatient(1L);

		assertEquals(patient, result);
	}

	@Test
	public void getPatient_WithNonExistentId_ShouldThrowNotFoundException() {
		Long nonExistentId = 1L;
		when(this.patientRepository.findById(nonExistentId)).thenReturn(Optional.empty());

		assertThrows(PatientNotFoundException.class, () -> patientService.getPatient(nonExistentId));
	}

	@Test
	public void getPatient_WithNegativeId_ShouldThrowInvalidIdException() {
		assertThrows(InvalidPatientIdException.class, () -> patientService.getPatient(-1L));
	}

	@Test
	public void getPatient_WithZeroId_ShouldThrowInvalidIdException() {
		assertThrows(InvalidPatientIdException.class, () -> patientService.getPatient(0L));
	}

	@Test
	public void addNewPatient_ShouldReturnPatient() {
		Patient patient = new Patient();
		when(this.patientRepository.save(patient)).thenReturn(patient);

		Patient result = patientService.addNewPatient(patient);

		assertEquals(patient, result);
	}

	@Test
	public void updatePatient_WhenPatientExists_ShouldUpdateAndSavePatient() {
		// Arrange
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));

		// Act
		patientService.updatePatient(patientId, patientDTO);

		// Assert
		verify(patientRepository).findById(patientId);
		verify(patientRepository).save(existingPatient);

		assertEquals(patientDTO.getName(), existingPatient.getName());
		assertEquals(patientDTO.getSurname(), existingPatient.getSurname());
		assertEquals(patientDTO.getEmail(), existingPatient.getEmail());
		assertEquals(patientDTO.getAge(), existingPatient.getAge());
		assertEquals(patientDTO.getWeight(), existingPatient.getWeight());
		assertEquals(patientDTO.getHeight(), existingPatient.getHeight());
	}

	@Test
	public void updatePatient_WhenPatientDoesNotExist_ShouldThrowException() {
		// Arrange
		when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(PatientNotFoundException.class, () -> {
			patientService.updatePatient(patientId, patientDTO);
		});

		verify(patientRepository).findById(patientId);
		verify(patientRepository, never()).save(any(Patient.class));
	}

	@Test
	public void updatePatient_WithNullValues_ShouldUpdateOnlyNonNullFields() {
		// Arrange
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));

		PatientDTO partialUpdateDTO = new PatientDTO();
		partialUpdateDTO.setName("NewName");
		partialUpdateDTO.setSurname("NewSurname");
		// email, age, weight, height are null

		// Act
		patientService.updatePatient(patientId, partialUpdateDTO);

		// Assert
		verify(patientRepository).findById(patientId);
		verify(patientRepository).save(existingPatient);

		assertEquals("NewName", existingPatient.getName());
		assertEquals("NewSurname", existingPatient.getSurname());
		// Original values should be retained for null fields
		assertEquals("jane.smith@example.com", existingPatient.getEmail());
		assertEquals(30, existingPatient.getAge());
		assertEquals(65.5, existingPatient.getWeight());
		assertEquals(165.0, existingPatient.getHeight());
	}

	@Test
	public void updatePatient_WithInvalidId_ShouldThrowException() {
		// Arrange
		Long invalidId = -1L;

		// Act & Assert
		assertThrows(InvalidPatientIdException.class, () -> {
			patientService.updatePatient(invalidId, patientDTO);
		});

		verify(patientRepository, never()).findById(invalidId);
		verify(patientRepository, never()).save(any(Patient.class));
	}

	@Test
	public void deletePatient_ShouldDeletePatient() {
		// Arrange
		when(patientRepository.existsById(patientId)).thenReturn(true);

		// Act
		patientService.deletePatient(patientId);

		// Assert
		verify(patientRepository).existsById(patientId);
		verify(patientRepository).deleteById(patientId);
	}

	@Test
	public void deletePatient_WithNonExistentId_ShouldThrowNotFoundException() {
		// Arrange
		Long nonExistentId = 1L;
		when(patientRepository.existsById(nonExistentId)).thenReturn(false);

		// Act & Assert
		assertThrows(PatientNotFoundException.class, () -> {
			patientService.deletePatient(nonExistentId);
		});

		verify(patientRepository).existsById(nonExistentId);
		verify(patientRepository, never()).deleteById(any());
	}

	@Test
	public void deletePatient_WithInvalidId_ShouldThrowInvalidIdException() {
		// Arrange
		Long invalidId = -1L;

		// Act & Assert
		assertThrows(InvalidPatientIdException.class, () -> {
			patientService.deletePatient(invalidId);
		});

		verify(patientRepository, never()).existsById(any());
		verify(patientRepository, never()).deleteById(any());
	}
}