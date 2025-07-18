package org.rhydo.microproduct.repositories;

import org.rhydo.microproduct.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.active = true AND LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%') ) ")
    List<Product> searchProducts(String keyword);

    @Query("SELECT p FROM Product p WHERE p.active = true AND p.id = ?1")
    Optional<Product> findByIdAndActiveTrue(Long id);
}
