package com.nutridata.patient;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
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

    @ExceptionHandler(InvalidPatientIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPatientId(InvalidPatientIdException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePatientNotFound(PatientNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addNewPatient(@RequestBody @Valid Patient patient) {
        this.patientService.addNewPatient(patient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        this.patientService.deletePatient(id);
    }
}
