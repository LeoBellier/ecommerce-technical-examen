package xyz.leobellier.ecommerce.orderItems.domain;

import org.junit.jupiter.api.Test;
import xyz.leobellier.ecommerce.products.domain.Product;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class OrderItemTest {
    @Test
    void shouldCreateOrderItemCorrectly() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(1200.0));
        OrderItem orderItem = new OrderItem(1L, product, 2);

        assertThat(orderItem.id()).isEqualTo(1L);
        assertThat(orderItem.product()).isEqualTo(product);
        assertThat(orderItem.quantity()).isEqualTo(2);

    }
}