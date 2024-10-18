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
        if (id <= 0) {
            throw new InvalidPatientIdException("Patient ID can not be negative or zero");
        }

        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));
    }

    public Patient addNewPatient(Patient patient) {
        return this.patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        this.patientRepository.deleteById(id);
    }
}
