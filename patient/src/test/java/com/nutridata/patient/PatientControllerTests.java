package com.nutridata.patient;

import com.nutridata.patient.Patient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
public class PatientControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;
    private static final String rootUri = "/api/patient";

    @Test
    public void getRootUri_ShouldReturnOkStatus() throws Exception {
        when(this.patientService.getPatients()).thenReturn(List.of(new Patient()));
        this.mockMvc.perform(get(rootUri)).andExpect(status().isOk());
    }

    @Test
    public void getRootUri_WithValidIdShould_ReturnOkStatus() throws Exception {
        when(this.patientService.getPatient(1L)).thenReturn(new Patient());
        this.mockMvc.perform(get(rootUri + "/1")).andExpect(status().isOk());
    }

    @Test
    public void getRootUri_WithInvalidId_ShouldReturnNotFoundStatus() throws Exception {
        when(this.patientService.getPatient(1L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with id 1 not found"));
        this.mockMvc.perform(get(rootUri + "/1")).andExpect(status().isNotFound());
    }

    @Test
    public void postRootUri_WithValidPatient_ShouldReturnCreatedStatus() throws Exception {
        String patientJson = "{\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"email@email.com\"}";

        this.mockMvc.perform(post(rootUri).contentType(MediaType.APPLICATION_JSON)
                .content(patientJson)).andExpect(status().isCreated());
    }

    @Test
    public void deletePatient_WitValidId_ShouldReturnNoContentStatus() throws Exception {
        doNothing().when(this.patientService).deletePatient(1L);
        this.mockMvc.perform(delete(rootUri + "/1")).andExpect(status().isNoContent());
    }
}
