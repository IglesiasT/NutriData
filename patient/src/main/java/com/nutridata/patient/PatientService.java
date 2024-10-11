package com.nutridata.patient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Patient with id " + id + " not found"));
    }

    public void addNewPatient(Patient patient) {
        patientRepository.save(patient);
    }
}
