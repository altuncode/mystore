package com.altuncode.mystore.repositories;

import com.altuncode.mystore.model.ProductCategory;
import com.altuncode.mystore.repositories.projection.CategoryWithSubCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductCategoryRepo")
public interface ProductCategoryRepo  extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT pc FROM ProductCategory pc ORDER BY pc.id DESC")
    Page<ProductCategory> findAllOrderedByIdDesc(Pageable pageable);

    @Query("SELECT c FROM ProductCategory c LEFT JOIN FETCH c.subCategories")
    List<CategoryWithSubCategories> findAllCategoriesWithSubCategories();

    // Fetch only active categories, ordered by orderNumber
    List<ProductCategory> findByActiveTrueOrderByOrderNumberAsc();
}
