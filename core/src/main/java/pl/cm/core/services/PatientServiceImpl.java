package pl.cm.core.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import pl.cm.core.dto.PatientDTO;
import pl.cm.core.exceptions.CommonException;
import pl.cm.core.exceptions.ExceptionType;
import pl.cm.core.mappings.PatientMapper;
import pl.cm.core.model.entities.Patient;
import pl.cm.core.model.entities.Visit;
import pl.cm.core.model.repositories.PatientRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public Long save(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setSerialNumber(dto.getSerialNumber());
        patient.setBirthday(dto.getBirthday());
        return patientRepository.save(patient).getId();
    }

    @Override
    public Long update(Long id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->new CommonException(ExceptionType.NOT_FOUND));
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setSerialNumber(dto.getSerialNumber());
        patient.setBirthday(dto.getBirthday());
        return patientRepository.save(patient).getId();
    }

    @Override
    public PatientDTO get(Long id) {
        return patientMapper.entityToDto(patientRepository
                .findById(id).orElseThrow(() ->new CommonException(ExceptionType.NOT_FOUND)));
    }

    @Override
    public Set<PatientDTO> getAll() {
         return patientMapper.entitySetToDtoSet(Sets.newHashSet(patientRepository.findAll()));
    }

    @Override
    public Set<PatientDTO> findAllByKeywords(String keyword) {
        return patientMapper.entitySetToDtoSet(Sets.newHashSet(patientRepository.findByKeywordsContainingIgnoreCase(keyword)));
    }

    @Override
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->new CommonException(ExceptionType.NOT_FOUND));
        patientRepository.delete(patient);
    }

    @Override
    public void addVisit(Long patientId, Visit visit) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->new CommonException(ExceptionType.NOT_FOUND));
        patient.addVisit(visit);
        patientRepository.save(patient);
    }

    @Override
    public Set<Visit> showVisitsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->new CommonException(ExceptionType.NOT_FOUND));
        return patient.getVisits();
    }
}
