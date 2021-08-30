package pl.cm.core.services;

import org.springframework.stereotype.Service;
import pl.cm.core.dto.PatientDTO;
import pl.cm.core.dto.VisitDTO;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class IntService {

    private final PatientService patientService;

    private final VisitService visitService;

    public IntService(PatientService patientService, VisitService visitService) {
        this.patientService = patientService;
        this.visitService = visitService;
    }

    public void run(){
        PatientDTO patientFirst = new PatientDTO(null, "Jan", "Kowalski", "111111111111", LocalDate.of(1988,1,3));
        PatientDTO patientSecond = new PatientDTO(null, "Jadwiga", "Bicz", "2222222222222", LocalDate.of(1956,5,25));

        Long patientFirstId = patientService.save(patientFirst);
        Long patientSecondId = patientService.save(patientSecond);

        VisitDTO visitFirst = new VisitDTO("dr. Przemyslaw Przemysloskie", "Zaplenie gardla", LocalDateTime.now().minusDays(5), patientFirstId);
        VisitDTO visitSecond = new VisitDTO("dr. Boleslaw Chrom", "Stan przedzawalowy", LocalDateTime.now().minusDays(10), patientSecondId);
        VisitDTO visitThree = new VisitDTO("dr. Boleslaw Chrom", "Wizita kontorlna", LocalDateTime.now().minusDays(2), patientSecondId);

        visitService.save(visitFirst);
        visitService.save(visitSecond);
        visitService.save(visitThree);

    }
}
