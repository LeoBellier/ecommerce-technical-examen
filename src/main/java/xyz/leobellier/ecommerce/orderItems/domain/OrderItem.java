package xyz.leobellier.ecommerce.orderItems.domain;

import jakarta.persistence.*;
import xyz.leobellier.ecommerce.products.domain.Product;

import java.io.Serializable;

@Entity
@Table(name = "order_items")
public record OrderItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        Product product,
        int quantity
) implements Serializable{
  public OrderItem(){
      this(null,null,0);
  }
}