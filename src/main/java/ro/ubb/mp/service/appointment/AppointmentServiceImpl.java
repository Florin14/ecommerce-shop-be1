package ro.ubb.mp.service.appointment;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import ro.ubb.mp.controller.dto.request.AppointmentRequestDTO;

import ro.ubb.mp.dao.model.postgres.Appointment;
import ro.ubb.mp.dao.model.postgres.Role;
import ro.ubb.mp.dao.model.postgres.User;
import ro.ubb.mp.dao.repository.postgres.AppointmentRepository;
import ro.ubb.mp.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service("appointmentService")
@Data
@Getter
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;
    private final UserService userService;

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Appointment createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        final User student = getUserService().getUserById(appointmentRequestDTO.getStudentId())
                .orElseThrow(EntityNotFoundException::new);
        final User mentor = getUserService().getUserById(appointmentRequestDTO.getMentorId())
                .orElseThrow(EntityNotFoundException::new);

        final Appointment appointmentToBeSaved = Appointment.builder()
                .student(student)
                .mentor(mentor)
                .date(LocalDateTime.parse(appointmentRequestDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .locationDetails(appointmentRequestDTO.getLocationDetails())
                .build();

        return repository.save(appointmentToBeSaved);
    }

    @Override
    public List<Appointment> getAllAppointmentsUser(User user) {
        if (user.getRole().equals(Role.STUDENT)) {
            return repository.getAppointmentsByStudent(user);
        } else {
            return repository.getAppointmentsByMentor(user);
        }
    }


    @Override
    public void deleteAppointmentById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Appointment updateAppointment(AppointmentRequestDTO appointmentRequestDTO, Long id) {
        Appointment appointment = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!(appointmentRequestDTO.getLocationDetails().isEmpty() || appointmentRequestDTO.getLocationDetails().isBlank())) {
            appointment.setLocationDetails(appointmentRequestDTO.getLocationDetails());
        }

        if (appointmentRequestDTO.getDate() != null) {
            appointment.setDate(LocalDateTime.parse(appointmentRequestDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        return repository.save(appointment);
    }


}
