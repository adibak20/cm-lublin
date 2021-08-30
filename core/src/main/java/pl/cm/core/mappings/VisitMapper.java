package pl.cm.core.mappings;

import org.mapstruct.Mapper;
import pl.cm.core.common.AbstractMapper;
import pl.cm.core.dto.VisitDTO;
import pl.cm.core.model.entities.Visit;

@Mapper(componentModel = "spring")
public abstract class VisitMapper extends AbstractMapper<VisitDTO, Visit> {
}
