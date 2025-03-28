package xyz.leobellier.ecommerce.shared.dto.product;

import xyz.leobellier.ecommerce.products.domain.Product;

import java.math.BigDecimal;

public record ProductResponse( Long id,
                               String name,
                               BigDecimal price) {
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(product.id(), product.name(), product.price());
    }
}
