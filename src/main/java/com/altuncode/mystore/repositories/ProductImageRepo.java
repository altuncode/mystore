package com.altuncode.mystore.repositories;

import com.altuncode.mystore.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductImageRepo")
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {

    @Query("SELECT i FROM ProductImage i WHERE i.product.id = :productId")
    List<ProductImage> findByProductId(Long productId);
}
