package pl.cm.core.services;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import pl.cm.core.dto.VisitDTO;
import pl.cm.core.exceptions.CommonException;
import pl.cm.core.exceptions.ExceptionType;
import pl.cm.core.mappings.VisitMapper;
import pl.cm.core.model.entities.Visit;
import pl.cm.core.model.repositories.VisitRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final PatientService patientService;
    private final VisitMapper visitMapper;
    private final VisitRepository visitRepository;

    public VisitServiceImpl(PatientService patientService, VisitMapper visitMapper, VisitRepository visitRepository) {
        this.patientService = patientService;
        this.visitMapper = visitMapper;
        this.visitRepository = visitRepository;
    }

    @Override
    public Long save(VisitDTO dto) {
        Visit visit = new Visit();
        visit.setDateOfVisit(dto.getDateOfVisit());
        visit.setDescription(dto.getDescription());
        visit.setDoctorName(dto.getDoctorName());
        Long id = visitRepository.save(visit).getId();
        patientService.addVisit(dto.getPatientId(), visit);
        return id;
    }

    @Override
    public Long update(Long id, VisitDTO dto) {
        Visit visit = visitRepository.findById(id).orElseThrow(()->new CommonException(ExceptionType.NOT_FOUND));
        visit.setDateOfVisit(dto.getDateOfVisit());
        visit.setDescription(dto.getDescription());
        visit.setDoctorName(dto.getDoctorName());
        return visitRepository.save(visit).getId();
    }

    @Override
    public VisitDTO get(Long id) {
        return visitMapper.entityToDto(visitRepository.findById(id).orElseThrow(()->new CommonException(ExceptionType.NOT_FOUND)));
    }

    @Override
    public Set<VisitDTO> getAllByPatient(Long patientId) {
        return visitMapper.entitySetToDtoSet(patientService.showVisitsByPatient(patientId));
    }

    @Override
    public void delete(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow(()->new CommonException(ExceptionType.NOT_FOUND));
        visitRepository.delete(visit);
    }
}
