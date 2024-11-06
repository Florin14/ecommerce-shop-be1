package ro.ubb.mp.controller.dto.response.product_size;

import lombok.Builder;
import lombok.Data;
import ro.ubb.mp.dao.model.ProductSize;

@Data
@Builder

public class ProductSizeStockResponseDTO {
    private Long id;
    private int stockQuantity;
    private ProductSize productSize;
}
