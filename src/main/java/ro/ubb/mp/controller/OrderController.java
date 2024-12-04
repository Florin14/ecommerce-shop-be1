package ro.ubb.mp.controller;

import ro.ubb.mp.controller.dto.mapper.BrandMapper;
import ro.ubb.mp.controller.dto.mapper.OrderMapper;
import ro.ubb.mp.controller.dto.request.BrandRequestDTO;
import ro.ubb.mp.controller.dto.request.OrderRequestDTO;
import ro.ubb.mp.controller.dto.response.brand.BrandResponseDTO;
import ro.ubb.mp.controller.dto.response.order.OrderResponseDTO;
import ro.ubb.mp.dao.model.Brand;
import ro.ubb.mp.dao.model.Order;
import ro.ubb.mp.service.brand.BrandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.ubb.mp.service.order.OrderService;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@Getter
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping()
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        List<Order> orders = orderService.getAll();
        List<OrderResponseDTO> orderResponseDTOS = orders.stream().map(order -> getOrderMapper().toDTO(order)).collect(Collectors.toList());

        return ResponseEntity.ok().body(orderResponseDTOS);
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO order) {
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addOrder").toUriString()));
        return ResponseEntity.created(uri).body(getOrderMapper().toDTO(getOrderService().saveOrder(order)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateBrand(@RequestBody OrderRequestDTO order,
                                                        @PathVariable Long id) {
        return ResponseEntity.ok().body(getOrderMapper().toDTO(getOrderService().
                updateOrder(order, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBrand(@PathVariable Long id) {

        final Order order = getOrderService().findById(id).
                orElseThrow(EntityNotFoundException::new);
        orderService.deleteOrderById(order.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
