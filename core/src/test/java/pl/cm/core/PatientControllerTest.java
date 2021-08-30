package pl.cm.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.cm.core.controllers.PatientController;
import pl.cm.core.dto.PatientDTO;
import pl.cm.core.services.IntService;
import pl.cm.core.services.PatientService;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private IntService intService;

    private String json = "{\"firstName\": \"test\",\"lastName\": \"test\",\"serialNumber\": \"8787878\"}";


    private PatientDTO patientDTO = new PatientDTO(null, "", "", "", LocalDate.now());

    @Test
    public void save() throws Exception {
        when(patientService.save(patientDTO)).thenReturn(1L);
        this.mockMvc.perform(post("/patient").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void update() throws Exception {
        when(patientService.update(1L, patientDTO)).thenReturn(1L);
        this.mockMvc.perform(patch("/patient/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void delete() throws Exception {
        when(patientService.update(1L, patientDTO)).thenReturn(1L);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/patient/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
