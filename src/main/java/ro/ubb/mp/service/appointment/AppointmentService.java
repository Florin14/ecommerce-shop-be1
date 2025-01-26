package ro.ubb.mp.service.appointment;

import ro.ubb.mp.controller.dto.request.AppointmentRequestDTO;
import ro.ubb.mp.dao.model.postgres.Appointment;
import ro.ubb.mp.dao.model.postgres.User;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Optional<Appointment> getAppointmentById(Long id);
    void deleteAppointmentById(Long id);
    Appointment updateAppointment(AppointmentRequestDTO appointmentRequestDTO, Long id);
    Appointment createAppointment(AppointmentRequestDTO appointmentRequestDTO);
    List<Appointment> getAllAppointmentsUser(User user);
}
