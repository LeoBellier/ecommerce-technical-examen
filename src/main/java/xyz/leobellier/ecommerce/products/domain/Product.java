package xyz.leobellier.ecommerce.products.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public record Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        String name,
        BigDecimal price) {
    public Product() {
        this(null, null, null);
    }
}
