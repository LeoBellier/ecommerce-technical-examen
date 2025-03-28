package xyz.leobellier.ecommerce.products.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.leobellier.ecommerce.products.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
