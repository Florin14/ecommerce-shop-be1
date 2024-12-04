package ro.ubb.mp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequestDTO {
    private Long userId;
    private List<OrderItemRequestDTO> orderItems;
}
