package ro.ubb.mp.controller.dto.mapper;
import ro.ubb.mp.controller.dto.request.MessageRequestDTO;
import ro.ubb.mp.dao.model.mysql.Message;


public interface MessageRequestMapper {
    MessageRequestDTO toDTO(Message message);
}
