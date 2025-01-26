package ro.ubb.mp.dao.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.mp.dao.model.postgres.Study;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
}
