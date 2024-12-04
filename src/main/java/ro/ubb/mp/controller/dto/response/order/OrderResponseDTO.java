package ro.ubb.mp.controller.dto.response.order;

import lombok.Builder;
import lombok.Data;
import ro.ubb.mp.dao.model.OrderItem;

import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String name;
    private List<OrderItem> orderItems;
}
