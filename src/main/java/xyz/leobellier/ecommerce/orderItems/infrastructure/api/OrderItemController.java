package xyz.leobellier.ecommerce.orderItems.infrastructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.leobellier.ecommerce.orderItems.application.OrderItemService;
import xyz.leobellier.ecommerce.orderItems.domain.OrderItem;
import xyz.leobellier.ecommerce.shared.dto.OrderItem.OrderItemRequest;
import xyz.leobellier.ecommerce.shared.dto.OrderItem.OrderItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAllOrderItems() {
        List<OrderItemResponse> orderItems = service.getAllOrderItem()
                .stream().map(OrderItemResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Long id){
        return service.getOrderItemById(id)
                .map(orderItem -> ResponseEntity.ok(OrderItemResponse.fromDomain(orderItem)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<OrderItemResponse> createOrderItem (@RequestBody OrderItemRequest request){
        OrderItem orderItem= service.createOrderItem(request);
        return ResponseEntity.ok(OrderItemResponse.fromDomain(orderItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemRequest request){
        return service.updateOrderItem(id,request)
                .map(updated -> ResponseEntity.ok(OrderItemResponse.fromDomain(updated)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id){
        service.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
