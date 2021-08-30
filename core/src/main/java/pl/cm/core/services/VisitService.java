package pl.cm.core.services;

import pl.cm.core.dto.VisitDTO;

import java.util.Set;

public interface VisitService {

    Long save(VisitDTO VisitDTO);
    Long update(Long id, VisitDTO VisitDTO);
    VisitDTO get(Long id);
    Set<VisitDTO> getAllByPatient(Long patientId);
    void delete(Long id);
}
