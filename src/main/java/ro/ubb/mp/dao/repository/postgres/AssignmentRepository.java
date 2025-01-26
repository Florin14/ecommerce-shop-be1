package ro.ubb.mp.dao.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.mp.dao.model.postgres.Assignment;
import ro.ubb.mp.dao.model.postgres.User;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByTitle(String title);

    List<Assignment> findByAuthor(User mentor);

    List<Assignment> findByStudents(User student);
}
