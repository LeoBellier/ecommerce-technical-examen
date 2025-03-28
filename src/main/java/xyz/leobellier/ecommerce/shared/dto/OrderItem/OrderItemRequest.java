package xyz.leobellier.ecommerce.shared.dto.OrderItem;

import xyz.leobellier.ecommerce.orderItems.domain.OrderItem;
import xyz.leobellier.ecommerce.products.domain.Product;

public record OrderItemRequest(Long productId, int quantity) {
    public OrderItem toDomain(Product product){
        return new OrderItem(null, product, quantity);
    }
}
