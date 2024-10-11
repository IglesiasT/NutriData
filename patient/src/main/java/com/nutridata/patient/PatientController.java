package com.nutridata.patient;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    @PostMapping
    public void addNewPatient(Patient patient) {
        patientService.addNewPatient(patient);
    }
}
