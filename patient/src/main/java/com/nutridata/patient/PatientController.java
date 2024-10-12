package com.nutridata.patient;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients() {
        return this.patientService.getPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return this.patientService.getPatient(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addNewPatient(@RequestBody @Valid Patient patient) {
        this.patientService.addNewPatient(patient);
    }
}
