package com.product.warehouse.repository;

import com.product.warehouse.entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByTitleOrderByPrice(String title);

    List<ProductEntity> findBy(Sort sort);

    List<ProductEntity> findByOrderByPrice();

//    List<ProductEntity> findByCreateAtAfter(LocalDateTime after);

//    List<ProductEntity> findByQuantityGraterThanOrPriceLessThan(int quantity, BigDecimal price);

    List<ProductEntity> findByTitleLike(String title);

    // List<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    List<ProductEntity> findByTitleContainingIgnoreCase(String title);

    @Query("select e.title , e.price from ProductEntity e where e.title=:title and e.price=:price")
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

}
