package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.brand.BrandResponseDTO;
import org.mapstruct.Mapper;
import ro.ubb.mp.controller.dto.response.order.OrderResponseDTO;
import ro.ubb.mp.dao.model.Order;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDTO toDTO(Order order);
}
