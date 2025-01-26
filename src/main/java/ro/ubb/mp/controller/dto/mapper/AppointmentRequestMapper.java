package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.request.AppointmentRequestDTO;
import ro.ubb.mp.dao.model.postgres.Appointment;


public interface AppointmentRequestMapper {
    AppointmentRequestDTO toDTO(Appointment appointment);
}
