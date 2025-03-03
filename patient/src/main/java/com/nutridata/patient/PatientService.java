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
        if (id <= 0) {
            throw new InvalidPatientIdException("Patient ID can not be negative or zero");
        }
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient with id " + id + " not found");
        }

        this.patientRepository.deleteById(id);
    }

    public void updatePatient(Long id, PatientDTO patientDTO) {
        Patient existingPatient = this.getPatient(id);

        if (patientDTO.getName() != null) existingPatient.setName(patientDTO.getName());
        if (patientDTO.getSurname() != null) existingPatient.setSurname(patientDTO.getSurname());
        if (patientDTO.getEmail() != null) existingPatient.setEmail(patientDTO.getEmail());
        if (patientDTO.getAge() != null) existingPatient.setAge(patientDTO.getAge());
        if (patientDTO.getWeight() != null) existingPatient.setWeight(patientDTO.getWeight());
        if (patientDTO.getHeight() != null) existingPatient.setHeight(patientDTO.getHeight());

        this.patientRepository.save(existingPatient);
    }
}
