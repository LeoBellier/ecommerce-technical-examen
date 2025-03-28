package xyz.leobellier.ecommerce.orderItems.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import xyz.leobellier.ecommerce.orderItems.application.OrderItemService;
import xyz.leobellier.ecommerce.orderItems.domain.OrderItem;
import xyz.leobellier.ecommerce.products.domain.Product;
import xyz.leobellier.ecommerce.shared.dto.OrderItem.OrderItemRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderItemService orderItemService;

    @Test
    public void shouldCreateOrderItem() throws Exception {
        OrderItemRequest request = new OrderItemRequest(1L, 2);
        OrderItem response = new OrderItem(1L, new Product(1L, "Producto", BigDecimal.valueOf(100.0)), 2);

        when(orderItemService.createOrderItem(any(OrderItemRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    public void shouldUpdateOrderItem() throws Exception {
        OrderItemRequest request = new OrderItemRequest(1L, 3);
        OrderItem response = new OrderItem(1L, new Product(1L, "Producto", BigDecimal.valueOf(100.0)), 3);

        when(orderItemService.createOrderItem(any(OrderItemRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/order-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(3));
    }

    @Test
    public void shouldGetOrderItemById() throws Exception {
        OrderItem response = new OrderItem(1L, new Product(1L, "Producto", BigDecimal.valueOf(100.0)), 2);

        when(orderItemService.getOrderItemById(1L).get()).thenReturn(response);

        mockMvc.perform(get("/api/order-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    public void shouldListAllOrderItems() throws Exception {
        List<OrderItem> items = List.of(
                new OrderItem(1L, new Product(1L, "Producto1", new BigDecimal("100.0")), 2),
                new OrderItem(2L, new Product(2L, "Producto2", new BigDecimal("200.0")), 3)
        );

        when(orderItemService.getAllOrderItem()).thenReturn(items);

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void shouldDeleteOrderItem() throws Exception {
        doNothing().when(orderItemService).deleteOrderItem(1L);

        mockMvc.perform(delete("/api/order-items/1"))
                .andExpect(status().isNoContent());
    }
}