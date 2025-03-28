package xyz.leobellier.ecommerce.orderItems.application;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.leobellier.ecommerce.orderItems.domain.OrderItem;
import xyz.leobellier.ecommerce.orderItems.infrastructure.persistence.OrderItemRepository;
import xyz.leobellier.ecommerce.products.domain.Product;
import xyz.leobellier.ecommerce.products.infrastructure.persistence.ProductRepository;
import xyz.leobellier.ecommerce.shared.dto.OrderItem.OrderItemRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock private ProductRepository productRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateOrderItemSuccessfully() {
        Long productId = 1L;
        Product product = new Product(productId, "Laptop", BigDecimal.valueOf(1200.0));
        OrderItemRequest request = new OrderItemRequest(productId, 2);
        OrderItem expectedOrderItem = new OrderItem(null, product, 2);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(expectedOrderItem);

        OrderItem result = orderItemService.createOrderItem(request);

        assertThat(result).isNotNull();
        assertThat(result.product()).isEqualTo(product);
        assertThat(result.quantity()).isEqualTo(2);

        verify(productRepository, times(1)).findById(productId);
            verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long productId = 99L;
        OrderItemRequest request = new OrderItemRequest(productId, 2);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderItemService.createOrderItem(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product not found");

        verify(productRepository, times(1)).findById(productId);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }


}