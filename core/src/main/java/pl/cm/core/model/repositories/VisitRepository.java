package pl.cm.core.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cm.core.model.entities.Visit;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

}

