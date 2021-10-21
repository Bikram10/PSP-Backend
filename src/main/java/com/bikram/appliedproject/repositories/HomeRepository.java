package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface HomeRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p ORDER BY p.createdDate DESC ")
    Page<Product> findAllOrderByDateDsc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.view DESC ")
    Page<Product> findAllProductByView(Pageable pageable);
}
