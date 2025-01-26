package ro.ubb.mp.dao.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubb.mp.dao.model.postgres.Appointment;
import ro.ubb.mp.dao.model.postgres.User;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> getAppointmentsByMentor(User mentor);
    List<Appointment> getAppointmentsByStudent(User student);

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT a.student_id " +
                    "FROM appointments a " +
                    "WHERE a.mentor_id=?1 ")
    List<Long> findAllStudentsByMentorId(Long mentorId);
}
