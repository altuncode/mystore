package com.altuncode.mystore.services.admin;

import com.altuncode.mystore.model.ProductSubCategory;
import com.altuncode.mystore.repositories.ProductSubCategoryRepo;
import com.altuncode.mystore.repositories.projection.ProductSubCategoryListProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ProductSubCategoryService")
@Transactional(readOnly = true)
public class ProductSubCategoryService {

    private final ProductSubCategoryRepo productSubCategoryRepo;

    @Autowired
    public ProductSubCategoryService(@Qualifier("ProductSubCategoryRepo") ProductSubCategoryRepo productSubCategoryRepo) {
        this.productSubCategoryRepo = productSubCategoryRepo;
    }

    // Get all subcategories with pagination
    public Page<ProductSubCategoryListProjection> getAllProductSubCategories(Pageable pageable) {
        return productSubCategoryRepo.findAllWithCategoryName(pageable);
    }

    // Get a single subcategory by ID
    public ProductSubCategory getProductSubCategoryById(Long id) {
        Optional<ProductSubCategory> productSubCategory = productSubCategoryRepo.findById(id);
        return productSubCategory.orElse(null);
    }

    @Transactional
    // Create or update a subcategory
    public ProductSubCategory saveProductSubCategory(ProductSubCategory productSubCategory) {
        return productSubCategoryRepo.save(productSubCategory);
    }

    @Transactional
    // Delete a subcategory by ID
    public void deleteProductSubCategoryById(Long id) {
        productSubCategoryRepo.deleteById(id);
    }


    public List<ProductSubCategory> findSubCategoriesByIds(List<Long> subCategoryIds) {
        return productSubCategoryRepo.findAllById(subCategoryIds);
    }
}