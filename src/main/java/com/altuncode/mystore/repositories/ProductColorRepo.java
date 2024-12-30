package com.altuncode.mystore.repositories;

import com.altuncode.mystore.model.ProductColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductColorRepo")
public interface ProductColorRepo extends JpaRepository<ProductColor, Long> {

    @Query("SELECT p FROM ProductColor p ORDER BY p.id DESC")
    Page<ProductColor> findAllOrderedByIdDesc(Pageable pageable);

    @Query("SELECT p FROM ProductColor p ORDER BY p.id DESC")
    List<ProductColor> findAllOrderedByIdDesc();

}
