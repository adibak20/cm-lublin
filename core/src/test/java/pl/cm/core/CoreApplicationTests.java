package pl.cm.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.cm.core.controllers.PatientController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CoreApplicationTests {

    @Autowired
    private PatientController patientController;

    @Test
    void contextLoads() {
        assertThat(patientController).isNotNull();
    }

}
