package pl.cm.core.services;

import pl.cm.core.dto.PatientDTO;
import pl.cm.core.model.entities.Patient;
import pl.cm.core.model.entities.Visit;

import java.util.List;
import java.util.Set;

public interface PatientService {
    Long save(PatientDTO patientDTO);
    Long update(Long id, PatientDTO patientDTO);
    PatientDTO get(Long id);
    Set<PatientDTO> getAll();
    Set<PatientDTO> findAllByKeywords(String keyword);
    void delete(Long id);
    void addVisit(Long patientId, Visit visit);
    Set<Visit> showVisitsByPatient(Long patientId);
}
