package xyz.leobellier.ecommerce.orderItems.application;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import xyz.leobellier.ecommerce.orderItems.domain.OrderItem;
import xyz.leobellier.ecommerce.orderItems.infrastructure.persistence.OrderItemRepository;
import xyz.leobellier.ecommerce.products.domain.Product;
import xyz.leobellier.ecommerce.products.infrastructure.persistence.ProductRepository;
import xyz.leobellier.ecommerce.shared.dto.OrderItem.OrderItemRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository repository;
    private final ProductRepository productRepository;

    public OrderItemService(OrderItemRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public List<OrderItem> getAllOrderItem() {
        return new ArrayList<>(repository.findAll());
    }

    public Optional<OrderItem> getOrderItemById(Long id) {
        return repository.findById(id);
    }

    public OrderItem createOrderItem(OrderItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return repository.save(request.toDomain(product));
    }

    public Optional<OrderItem> updateOrderItem(Long id, OrderItemRequest updateOrderItem) {
        Product product = productRepository.findById(updateOrderItem.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return repository.findById(id)
                .map(existingOrderItem -> {
                    OrderItem orderItemToSave = new OrderItem(id, product, updateOrderItem.quantity());
                    return repository.save(orderItemToSave);
                });
    }

    public void deleteOrderItem(Long id) {
        repository.deleteById(id);
    }
}
