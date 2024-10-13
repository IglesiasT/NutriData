package com.nutridata.patient;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTests {
	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private PatientService patientService;

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

		assertThrows(PatientNotFoundException.class, () -> {
			patientService.getPatient(nonExistentId);
		});
	}

}
