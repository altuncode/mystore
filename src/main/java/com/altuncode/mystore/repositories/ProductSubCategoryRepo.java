package com.altuncode.mystore.repositories;

import com.altuncode.mystore.model.ProductCategory;
import com.altuncode.mystore.model.ProductSubCategory;
import com.altuncode.mystore.repositories.projection.ProductSubCategoryListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductSubCategoryRepo")
public interface ProductSubCategoryRepo extends JpaRepository<ProductSubCategory, Long> {

    // Find all subcategories ordered by ID in descending order with pagination
    @Query("SELECT p.id AS id, p.name AS subCategoryName, p.productCategory.name AS categoryName FROM ProductSubCategory p ORDER BY p.id DESC")
    Page<ProductSubCategoryListProjection> findAllWithCategoryName(Pageable pageable);

    // Fetch only active subcategories for a specific category, ordered by orderNumber
    List<ProductSubCategory> findByProductCategoryAndActiveTrueOrderByOrderNumberAsc(ProductCategory productCategory);
}