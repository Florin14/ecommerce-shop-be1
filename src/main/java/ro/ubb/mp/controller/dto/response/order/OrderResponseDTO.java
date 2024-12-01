package ro.ubb.mp.controller.dto.response.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String name;
}
