package xyz.leobellier.ecommerce.shared.dto.OrderItem;

import xyz.leobellier.ecommerce.orderItems.domain.OrderItem;
import xyz.leobellier.ecommerce.shared.dto.product.ProductResponse;

public record OrderItemResponse(Long id, ProductResponse productResponse, int quantity) {
    public static OrderItemResponse fromDomain(OrderItem orderItem) {
        ProductResponse productResponse = ProductResponse.fromDomain(orderItem.product());
        return new OrderItemResponse(orderItem.id(), productResponse, orderItem.quantity());
    }
}
