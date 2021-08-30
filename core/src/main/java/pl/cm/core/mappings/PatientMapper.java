package pl.cm.core.mappings;

import org.mapstruct.Mapper;
import pl.cm.core.common.AbstractMapper;
import pl.cm.core.dto.PatientDTO;
import pl.cm.core.model.entities.Patient;

@Mapper(componentModel = "spring")
public abstract class PatientMapper extends AbstractMapper<PatientDTO, Patient> {
}
